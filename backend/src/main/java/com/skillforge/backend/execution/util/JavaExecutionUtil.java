package com.skillforge.backend.execution.util;

import com.skillforge.backend.execution.dto.response.CompilationResult;
import com.skillforge.backend.execution.dto.response.ExecutionResult;
import lombok.experimental.UtilityClass;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

@UtilityClass
public class JavaExecutionUtil {
    private static final long TIME_LIMIT_SECONDS = 2;
    public Path createSourceFile(String sourceCode) throws IOException{
        Path tempDirectory= Files.createTempDirectory("skillforge-");
        Path sourceFile=tempDirectory.resolve("Main.java");
        Files.writeString(sourceFile,sourceCode,StandardCharsets.UTF_8);
        return sourceFile;
    }

    public CompilationResult compile(Path sourceFile) throws IOException, InterruptedException{
        ProcessBuilder processBuilder = new ProcessBuilder("javac",sourceFile.toString());
        Process process=processBuilder.start();
        int exitcode=process.waitFor();
        if(exitcode==0){
            return new CompilationResult(true,"");
        }
        String error=new String(process.getErrorStream().readAllBytes()).trim();
        return new CompilationResult(false,error);
    }

    public ExecutionResult run(Path sourceFile,String input) throws IOException, InterruptedException{
        ProcessBuilder processBuilder = new ProcessBuilder(
                "java",
                "-cp",
                sourceFile.getParent().toString(),
                "Main");
        Process process = processBuilder.start();

        if(input!=null && !input.isBlank()){
            try(BufferedWriter writer=new BufferedWriter(new OutputStreamWriter(process.getOutputStream()))){
                writer.write(input);
                writer.flush();
            }
        }else{
            process.getOutputStream().close();
        }

        long start=System.currentTimeMillis();
        boolean finished = process.waitFor(TIME_LIMIT_SECONDS, TimeUnit.SECONDS);
        long end=System.currentTimeMillis();

        if(!finished){
            process.destroyForcibly();
            return new ExecutionResult(
                    false,
                    true,
                    "",
                    "Execution exceeded time limit.",
                    end - start
            );
        }
        int exitcode =process.exitValue();
        String output = new String(process.getInputStream().readAllBytes(), StandardCharsets.UTF_8);

        String error = new String(process.getErrorStream().readAllBytes(),StandardCharsets.UTF_8).trim();

        return new ExecutionResult(exitcode==0,false,output,error,end-start);
    }

    public void deleteDirectory(Path directory) throws IOException {

        if (directory == null || Files.notExists(directory)) {
            return;
        }

        try (Stream<Path> paths = Files.walk(directory)) {
            paths.sorted(Comparator.reverseOrder())
                    .forEach(path -> {
                        try {
                            Files.deleteIfExists(path);
                        } catch (IOException e) {
                            throw new UncheckedIOException(e);
                        }
                    });
        }
    }


}
