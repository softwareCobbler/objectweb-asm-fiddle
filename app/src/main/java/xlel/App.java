package xlel;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;

public class App {
    public static Path sourceAbsPath = Paths.get("build/classes/java/main/xlel/X.class").toAbsolutePath();
    public static Path targetAbsPath = Paths.get("build/classes/java/main/xlel/X2.class").toAbsolutePath();

    public static void main(String[] args) {
        doit();
    }

    public static byte[] readAllBytes(Path path) {
        try (var f = new FileInputStream(path.toFile())) {
            return f.readAllBytes();
        }
        catch (Throwable e) {
            e.printStackTrace();
            System.exit(1);
            return null;
        }
    }

    public static void writeAllBytes(Path path, byte[] bytes) {
        try (var f = new FileOutputStream(path.toFile())) {
            f.write(bytes);
        }
        catch (Throwable e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public static void doit() {
        try {
            var classWriter = new ClassWriter(ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS);
            var visitor = new XVisitor(Opcodes.ASM9, classWriter);
            var classReader = new ClassReader(readAllBytes(sourceAbsPath));
            classReader.accept(visitor, ClassReader.EXPAND_FRAMES);
            writeAllBytes(targetAbsPath, classWriter.toByteArray());
        }
        catch (Throwable e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public static class XVisitor extends ClassVisitor {
        XVisitor(int apiVersion, ClassWriter writer) {
            super(apiVersion, writer);
        }

        @Override
        public void visit(
            int version,
            int access,
            String name,
            String signature,
            String superName,
            String[] interfaces
        ) {
            super.visit(version, access, "xlel/X2", signature, superName, interfaces);
        }
    }
}
