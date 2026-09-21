/*
 * Copyright (C) ExBin Project, https://exbin.org
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.exbin.jaguif.addon.manager.operation;

import org.exbin.jaguif.addon.manager.api.operation.AddonModificationType;
import org.exbin.jaguif.addon.manager.api.operation.AddonModification;
import org.exbin.jaguif.addon.manager.ApplicationModulesUsage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jspecify.annotations.Nullable;
import org.jspecify.annotations.NullMarked;
import org.exbin.jaguif.App;
import org.exbin.jaguif.addon.manager.api.AddonRecord;
import org.exbin.jaguif.addon.manager.AddonUpdateChanges;
import org.exbin.jaguif.addon.manager.api.DependencyRecord;
import org.exbin.jaguif.addon.manager.api.ItemRecord;
import org.exbin.jaguif.addon.manager.DownloadItemRecord;
import org.exbin.jaguif.addon.manager.LicenseItemRecord;
import org.exbin.jaguif.addon.manager.api.AddonResolutionServiceException;
import org.exbin.jaguif.addon.manager.settings.AddonManagerOptions;
import org.exbin.jaguif.basic.BasicModuleProvider;
import org.exbin.jaguif.language.api.LanguageModuleApi;
import org.exbin.jaguif.options.api.OptionsModuleApi;
import org.exbin.jaguif.addon.manager.api.AddonResolutionService;

/**
 * Addon modifications operation.
 */
@NullMarked
public class AddonModificationsOperation {

    protected static final String MAVEN_CENTRAL_URL = "https://repo1.maven.org/maven2/";
    protected final ResourceBundle resourceBundle = App.getModule(LanguageModuleApi.class).getBundle(AddonModificationsOperation.class);

    protected final String primarySpdxLicense = "Apache-2.0";
    protected final AddonResolutionService resolutionService;
    protected final AddonUpdateChanges addonUpdateChanges;
    protected final ApplicationModulesUsage applicationModulesUsage;
    protected final List<LicenseItemRecord> licenseRecords = new ArrayList<>();
    protected final Set<String> licenseCodes = new HashSet<>();
    protected final Set<String> availableUpdates = new HashSet<>();

    protected final Map<AddonModificationType, List<?>> modifications = new HashMap<>();

    public AddonModificationsOperation(AddonResolutionService resolutionService, ApplicationModulesUsage applicationModulesUsage, AddonUpdateChanges addonUpdateChanges) {
        this.resolutionService = resolutionService;
        this.applicationModulesUsage = applicationModulesUsage;
        this.addonUpdateChanges = addonUpdateChanges;
    }

    public AddonUpdateChanges getAddonUpdateChanges() {
        return addonUpdateChanges;
    }

    public List<String> getOperations() {
        List<String> operations = new ArrayList<>();
        String operationMessage = resourceBundle.getString("operationMessage.installModule");
        for (Object identifier : getModifications(LocalAddonModificationType.INSTALL_ADDON)) {
            String moduleId = (String) identifier;
            operations.add(String.format(operationMessage, moduleId));
        }
        operationMessage = resourceBundle.getString("operationMessage.removeModule");
        for (Object identifier : getModifications(LocalAddonModificationType.REMOVE_ADDON)) {
            String moduleId = (String) identifier;
            operations.add(String.format(operationMessage, moduleId));
        }
        operationMessage = resourceBundle.getString("operationMessage.dependencyAddon");
        for (Object identifier : getModifications(LocalAddonModificationType.DEPENDENCY_ADDON)) {
            String moduleId = (String) identifier;
            operations.add(String.format(operationMessage, moduleId));
        }
        operationMessage = resourceBundle.getString("operationMessage.downloadLibrary");
        for (Object identifier : getModifications(LocalAddonModificationType.DOWNLOAD_LIBRARY)) {
            String libraryFile = (String) identifier;
            operations.add(String.format(operationMessage, libraryFile));
        }
        operationMessage = resourceBundle.getString("operationMessage.downloadMavenLibrary");
        for (Object identifier : getModifications(LocalAddonModificationType.DOWNLOAD_MAVEN_LIBRARY)) {
            String libraryFile = (String) identifier;
            operations.add(String.format(operationMessage, libraryFile));
        }
        operationMessage = resourceBundle.getString("operationMessage.removeLibrary");
        for (Object identifier : getModifications(LocalAddonModificationType.REMOVE_LIBRARY)) {
            String libraryFile = (String) identifier;
            operations.add(String.format(operationMessage, libraryFile));
        }
        return operations;
    }

    public List<LicenseItemRecord> getLicenseRecords() {
        for (LicenseItemRecord record : licenseRecords) {
            try {
                record.setUrl(resolutionService.getLicenseDownloadUrl(record.getRemoteFile()));
            } catch (AddonResolutionServiceException ex) {
                Logger.getLogger(AddonModificationsOperation.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return licenseRecords;
    }

    public List<DownloadItemRecord> getDownloadRecords() {
        List<DownloadItemRecord> downloadRecords = new ArrayList<>();
        String downloadItemDescription = resourceBundle.getString("downloadItemDescription.module");
        for (Object identifier : getModifications(LocalAddonModificationType.DOWNLOAD_MODULE)) {
            String moduleFile = (String) identifier;
            DownloadItemRecord record = new DownloadItemRecord(String.format(downloadItemDescription, moduleFile), moduleFile);
            try {
                record.setUrl(resolutionService.getFileDownloadUrl(moduleFile));
                downloadRecords.add(record);
            } catch (AddonResolutionServiceException ex) {
                Logger.getLogger(AddonModificationsOperation.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        downloadItemDescription = resourceBundle.getString("downloadItemDescription.library");
        for (Object identifier : getModifications(LocalAddonModificationType.DOWNLOAD_LIBRARY)) {
            String library = (String) identifier;
            DownloadItemRecord record = new DownloadItemRecord(String.format(downloadItemDescription, library), library);
            try {
                record.setUrl(resolutionService.getFileDownloadUrl(library));
                downloadRecords.add(record);
            } catch (AddonResolutionServiceException ex) {
                Logger.getLogger(AddonModificationsOperation.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        downloadItemDescription = resourceBundle.getString("downloadItemDescription.mavenLibrary");
        for (Object identifier : getModifications(LocalAddonModificationType.DOWNLOAD_MAVEN_LIBRARY)) {
            String library = (String) identifier;
            String libraryFile = BasicModuleProvider.mavenCodeToFileName(library);
            DownloadItemRecord record = new DownloadItemRecord(String.format(downloadItemDescription, library), libraryFile);
            try {
                record.setUrl(new URI(AddonModificationsOperation.mavenCodeToDownloadUrl(library)).toURL());
                downloadRecords.add(record);
            } catch (MalformedURLException | URISyntaxException ex) {
                Logger.getLogger(AddonModificationsOperation.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return downloadRecords;
    }

    public void installItem(ItemRecord item) {
        if (item instanceof AddonRecord) {
            String addonId = item.getId();
            if (addonUpdateChanges.hasInstallAddon(addonId)) {
                throw new IllegalStateException("Addon already queued for installation: " + addonId);
            }
            processAddonLicense((AddonRecord) item);
            try {
                addModification(resolutionService.getAddonFile(addonId));
                addModification(LocalAddonModificationType.INSTALL_ADDON, addonId);
            } catch (AddonResolutionServiceException ex) {
                Logger.getLogger(AddonModificationsOperation.class.getName()).log(Level.SEVERE, null, ex);
            }
            addAddonDependencies((AddonRecord) item);
        } else {
            throw new IllegalStateException("Unable to install non-addon item");
        }
    }

    public void updateItem(ItemRecord item, ItemRecord previousItem) {
        if (item instanceof AddonRecord) {
            String addonId = item.getId();
            if (addonUpdateChanges.hasInstallAddon(addonId)) {
                throw new IllegalStateException("Addon already queued for installation: " + addonId);
            }
            addModification(LocalAddonModificationType.INSTALL_ADDON, addonId);
            processAddonLicense((AddonRecord) item);
            if (previousItem.isAddon()) {
                String addonFile = findAddonFileName(item.getId());
                if (addonFile != null) {
                    addModification(LocalAddonModificationType.REMOVE_LIBRARY, addonFile);
                }
            }
            try {
                addModification(resolutionService.getAddonFile(item.getId()));
            } catch (AddonResolutionServiceException ex) {
                Logger.getLogger(AddonModificationsOperation.class.getName()).log(Level.SEVERE, null, ex);
            }
            addAddonDependencies((AddonRecord) item);
        } else {
            throw new IllegalStateException("Unable to update non-addon item");
        }
    }

    public void removeItem(ItemRecord item) {
        if (item instanceof AddonRecord) {
            String addonId = item.getId();
            if (addonUpdateChanges.hasRemoveAddon(addonId)) {
                throw new IllegalStateException("Addon already queued for removal: " + addonId);
            }
            addModification(LocalAddonModificationType.REMOVE_ADDON, addonId);
            String addonFile = findAddonFileName(item.getId());
            if (addonFile != null) {
                addModification(LocalAddonModificationType.REMOVE_LIBRARY, addonFile);
            }
        } else {
            throw new IllegalStateException("Unable to install non-addon item");
        }
    }

    @Nullable
    private static String findAddonFileName(String moduleId) {
        // TODO Replace with including file name in module records
        File targetDirectory = new File(App.getConfigDirectory(), "addons");
        if (!targetDirectory.isDirectory()) {
            return null;
        }

        File[] addonFiles = targetDirectory.listFiles();
        if (addonFiles == null) {
            return null;
        }
        for (File addonFile : addonFiles) {
            if (addonFile.getName().endsWith(".jar")) {
                try {
                    URL moduleRecordUrl = new URI("jar:" + addonFile.toURI().toURL().toExternalForm() + "!/META-INF/module.xml").toURL();
                    try (BufferedReader reader = new BufferedReader(new InputStreamReader(moduleRecordUrl.openStream()))) {
                        String line = reader.readLine();
                        do {
                            line = reader.readLine();
                            if (line != null && !line.isEmpty()) {
                                int idTag = line.indexOf("<id>");
                                if (idTag >= 0) {
                                    int end = line.indexOf("</id>");
                                    String fileModuleId = line.substring(idTag + 4, end);
                                    if (moduleId.equals(fileModuleId)) {
                                        return addonFile.getName();
                                    }
                                } else {
                                    int apiTag = line.indexOf("<api>");
                                    if (apiTag >= 0) {
                                        int end = line.indexOf("</api>");
                                        String fileModuleId = line.substring(apiTag + 5, end);
                                        if (moduleId.equals(fileModuleId)) {
                                            return addonFile.getName();
                                        }
                                    } else {
                                        int pluginTag = line.indexOf("<plugin>");
                                        if (pluginTag >= 0) {
                                            int end = line.indexOf("</plugin>");
                                            String fileModuleId = line.substring(pluginTag + 8, end);
                                            if (moduleId.equals(fileModuleId)) {
                                                return addonFile.getName();
                                            }
                                        }
                                    }
                                }
                            }
                        } while (line != null);
                    } catch (FileNotFoundException ex) {
                    } catch (NumberFormatException | IOException ex) {
                        Logger.getLogger(AddonModificationsOperation.class.getName()).log(Level.SEVERE, "Failed to read modules update cache", ex);
                    }
                } catch (MalformedURLException | URISyntaxException ex) {
                    Logger.getLogger(AddonModificationsOperation.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        return null;
    }

    private void addAddonDependencies(AddonRecord record) {
        List<DependencyRecord> dependencies = new ArrayList<>();
        dependencies.addAll(record.getDependencies());
        while (!dependencies.isEmpty()) {
            DependencyRecord dependency = dependencies.remove(0);
            DependencyRecord.Type dependencyType = dependency.getType();
            String dependencyId = dependency.getId();
            switch (dependencyType) {
                case MODULE:
                case PLUGIN:
                    boolean include = true;

                    if (containModification(LocalAddonModificationType.INSTALL_ADDON, dependencyId) || containModification(LocalAddonModificationType.DEPENDENCY_ADDON, dependencyId)) {
                        include = false;
                    } else if (applicationModulesUsage.hasModule(dependencyId) && !availableUpdates.contains(dependencyId)) {
                        include = false;
                    }

                    if (include) {
                        AddonRecord addonRecord;
                        try {
                            addonRecord = resolutionService.getAddonDependency(dependencyId);
                            addModification(LocalAddonModificationType.DEPENDENCY_ADDON, addonRecord.getId());
                            processAddonLicense(addonRecord);
                            addModification(resolutionService.getAddonFile(addonRecord.getId()));
                            dependencies.addAll(addonRecord.getDependencies());
                        } catch (AddonResolutionServiceException ex) {
                            Logger.getLogger(AddonModificationsOperation.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    break;
                case JAR_LIBRARY:
                    if (!applicationModulesUsage.hasLibrary(dependencyId) && !containModification(LocalAddonModificationType.DOWNLOAD_LIBRARY, dependencyId)) {
                        addModification(LocalAddonModificationType.DOWNLOAD_LIBRARY, dependencyId);
                    }
                    break;
                case MAVEN_LIBRARY:
                    if (!applicationModulesUsage.hasLibrary(BasicModuleProvider.mavenCodeToFileName(dependencyId)) && !containModification(LocalAddonModificationType.DOWNLOAD_MAVEN_LIBRARY, dependencyId)) {
                        addModification(LocalAddonModificationType.DOWNLOAD_MAVEN_LIBRARY, dependencyId);
                    }
                    break;
            }
        }
    }

    public void processAddonLicense(AddonRecord addonRecord) {
        String remoteFile = addonRecord.getLicenseRemoteFile();
        if (primarySpdxLicense.equals(addonRecord.getLicenseSpdx().orElse(null)) || remoteFile.isEmpty()) {
            return;
        }
        if (!licenseCodes.contains(remoteFile)) {
            licenseCodes.add(remoteFile);
            licenseRecords.add(new LicenseItemRecord(addonRecord.getLicense(), remoteFile));
        }
    }

    public void finished() {
        for (Object identifier : getModifications(LocalAddonModificationType.INSTALL_ADDON)) {
            String moduleId = (String) identifier;
            addonUpdateChanges.removeRemoveAddon(moduleId);
            addonUpdateChanges.addInstallAddon(moduleId);
        }
        for (Object identifier : getModifications(LocalAddonModificationType.DEPENDENCY_ADDON)) {
            String moduleId = (String) identifier;
            addonUpdateChanges.removeRemoveAddon(moduleId);
            addonUpdateChanges.addInstallAddon(moduleId);
        }
        for (Object identifier : getModifications(LocalAddonModificationType.DOWNLOAD_MODULE)) {
            String moduleFile = (String) identifier;
            addonUpdateChanges.removeRemoveFile(moduleFile);
            addonUpdateChanges.addUpdateFile(moduleFile);
        }
        for (Object identifier : getModifications(LocalAddonModificationType.DOWNLOAD_LIBRARY)) {
            String libraryFile = (String) identifier;
            addonUpdateChanges.removeRemoveFile(libraryFile);
            addonUpdateChanges.addUpdateFile(libraryFile);
        }
        for (Object identifier : getModifications(LocalAddonModificationType.DOWNLOAD_MAVEN_LIBRARY)) {
            String libraryFile = (String) identifier;
            addonUpdateChanges.removeRemoveFile(libraryFile);
            addonUpdateChanges.addUpdateFile(libraryFile);
        }
        for (Object identifier : getModifications(LocalAddonModificationType.REMOVE_ADDON)) {
            String moduleId = (String) identifier;
            addonUpdateChanges.removeInstallAddon(moduleId);
            if ("org.exbin.jaguif.addon.manager.AddonManagerModule".equals(moduleId)) {
                OptionsModuleApi preferencesModule = App.getModule(OptionsModuleApi.class);
                AddonManagerOptions addonOptions = new AddonManagerOptions(preferencesModule.getAppOptions());
                addonOptions.setActivatedVersion("0.3.0-SNAPSHOT");
            }
            addonUpdateChanges.addRemoveAddon(moduleId);
        }
        for (Object identifier : getModifications(LocalAddonModificationType.REMOVE_LIBRARY)) {
            String file = (String) identifier;
            addonUpdateChanges.removeUpdateFile(file);
            // TODO delete file
            addonUpdateChanges.addRemoveFile(file);
        }
        addonUpdateChanges.writeConfigFile();
    }

    public static String mavenCodeToDownloadUrl(String mavenCode) {
        StringBuilder builder = new StringBuilder();
        builder.append(MAVEN_CENTRAL_URL);
        int namePos = mavenCode.indexOf(":");
        if (namePos == -1) {
            throw new IllegalStateException("Maven library code is missing split characters: " + mavenCode);
        }
        int domainSegment = 0;
        while (domainSegment < namePos) {
            int segment = mavenCode.indexOf(".", domainSegment);
            if (segment == -1) {
                segment = namePos;
            } else if (segment > namePos) {
                segment = namePos;
            }
            builder.append(mavenCode.substring(domainSegment, segment)).append("/");
            domainSegment = segment + 1;
        }
        int versionPos = mavenCode.indexOf(":", namePos + 1);
        if (versionPos == -1) {
            throw new IllegalStateException("Maven library code is missing split characters: " + mavenCode);
        }
        String namePart = mavenCode.substring(namePos + 1, versionPos);
        String versionPart = mavenCode.substring(versionPos + 1);
        builder.append(namePart).append("/").append(versionPart).append("/");
        builder.append(namePart).append("-").append(versionPart).append(".jar");
        return builder.toString();
    }
    
    public Collection<?> getModifications(AddonModificationType type) {
        List<?> list = modifications.get(type);
        return list == null ? Collections.emptyList() : list;
    }
    
    public void addModification(AddonModificationType type, Object identifier) {
        List<?> list = modifications.get(type);
        if (list == null) {
            list = new ArrayList<>();
            modifications.put(type, list);
        }
        ((List) list).add(identifier);
    }
    
    public void addModification(AddonModification modification) {
        if (LocalAddonModificationType.NO_ACTION.equals(modification.getModificationType())) {
            return;
        }

        addModification(modification.getModificationType(), modification.getIdentifier());
    }
    
    public boolean containModification(AddonModificationType type, String identifier) {
        List<?> identifiers = modifications.get(type);
        if (identifiers == null) {
            return false;
        }

        return identifiers.contains(identifier);
    }
}
