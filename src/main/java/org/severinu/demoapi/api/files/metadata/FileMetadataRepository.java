package org.severinu.demoapi.api.files.metadata;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface FileMetadataRepository extends MongoRepository<FileMetadata, String> {
}
