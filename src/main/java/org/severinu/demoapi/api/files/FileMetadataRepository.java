package org.severinu.demoapi.api.files;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface FileMetadataRepository extends MongoRepository<FileMetadata, String> {
}
