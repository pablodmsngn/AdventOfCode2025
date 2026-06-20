package software.ulpgc.aoc.day01.application;

import software.ulpgc.aoc.day01.io.InstructionLoader;
import software.ulpgc.aoc.day01.model.Instruction;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;


public class ResourceInstructionLoader implements InstructionLoader {
    private final String resourceName;

    public ResourceInstructionLoader(String resourceName) {
        this.resourceName = resourceName;
    }

    @Override
    public List<Instruction> loadAll() {
        try {
            URL resource = getClass().getClassLoader().getResource(resourceName);
            if (resource == null) throw new RuntimeException("File not found: " + resourceName);

            Path path = Paths.get(resource.toURI());
            try (Stream<String> lines = Files.lines(path)) {
                return lines
                        .map(Instruction::of)
                        .flatMap(Optional::stream)
                        .toList();
            }
        } catch (Exception e) {
            throw new RuntimeException("Error loading input", e);
        }
    }
}
