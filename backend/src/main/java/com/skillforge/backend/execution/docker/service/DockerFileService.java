package com.skillforge.backend.execution.docker.service;

import com.skillforge.backend.execution.docker.model.Workspace;
import com.skillforge.backend.execution.enums.Language;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Comparator;

@Service
@Slf4j
public class DockerFileService {
    private static final String ROOT_DIRECTORY = "skillforge";


    public Workspace createWorkspace(String sourceCode, Language language) {

        try {

            Path workspace =
                    Files.createTempDirectory(ROOT_DIRECTORY + "-");

            Path sourceFile = workspace.resolve(language.getSourceFileName());

            Files.writeString(
                    sourceFile,
                    sourceCode,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.TRUNCATE_EXISTING
            );

            log.info(
                    "Workspace created. Directory: {}, Source: {}",
                    workspace,
                    sourceFile
            );

            return new Workspace(workspace, sourceFile);

        } catch (IOException ex) {
            throw new RuntimeException("Failed to create workspace", ex);
        }
    }


    public void deleteWorkspace(Workspace workspace) {

        if (workspace == null) {
            return;
        }

        try {

            Files.walk(workspace.directory())
                    .sorted(Comparator.reverseOrder())
                    .forEach(path -> {

                        try {
                            Files.deleteIfExists(path);
                        } catch (IOException e) {
                            log.warn("Unable to delete {}", path,e);
                        }

                    });

            log.info("Workspace deleted {}", workspace.directory());

        } catch (IOException e) {

            log.warn("Unable to cleanup workspace {}", workspace.directory(),e);

        }

    }
}
