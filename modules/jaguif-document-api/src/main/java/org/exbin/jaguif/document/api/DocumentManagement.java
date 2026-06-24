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
package org.exbin.jaguif.document.api;

import java.util.Optional;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.NullMarked;

/**
 * Interface for document management.
 */
@NullMarked
public interface DocumentManagement {

    /**
     * Registers document provider.
     *
     * @param documentProvider document provider
     */
    void registerDocumentProvider(DocumentProvider documentProvider);

    /**
     * Registers document type.
     *
     * @param documentType document type
     */
    void registerDocumentType(DocumentType documentType);

    /**
     * Creates default document.
     *
     * @return document
     */
    @NonNull
    Document createDefaultDocument();

    /**
     * Opens document from given source.
     *
     * @param source document source
     * @return document
     */
    @NonNull
    Document createDocumentForSource(SourceIdentifier source);

    /**
     * Creates document source for empty document.
     *
     * @return empty document source
     */
    @NonNull
    EmptyDocumentSource createEmptyDocumentSource();

    /**
     * Invokes opening of document using default method - typically opens file
     * chooser dialog.
     *
     * @return document or empty if failed / cancelled
     */
    @NonNull
    Optional<Document> openDefaultDocument();

    /**
     * Invokes save document as - typically opens file chooser dialog.
     *
     * @param document document to save
     * @return document source if selected
     */
    @NonNull
    Optional<DocumentSource> saveDocumentAs(Document document);

    /**
     * Adds document receiver.
     *
     * @param documentReceiver document receiver
     */
    void addDocumentReceiver(DocumentReceiver documentReceiver);

    /**
     * Receives document for further processing.
     *
     * @param document document
     */
    void receiveDocument(Document document);
}
