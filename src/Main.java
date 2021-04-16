import java.nio.file.*;
import java.io.File;

public class Main
{
    private static void simpleCopy(Path src, Path dest) throws Exception
    {
        Files.walk(src).forEach(source ->
        {
            Path destPath = source.subpath(1, source.getNameCount());
            Path destination = dest.resolve(destPath);

            System.out.println("Copy: " + source + " --> " + destination);

            if (destination.toFile().isDirectory() && destination.toFile().exists())
            {
                return;
            }

            try
            {
                Files.copy(source, destination, StandardCopyOption.REPLACE_EXISTING);
            }
            catch (Exception exc)
            {
                exc.printStackTrace();
            }
        });
    }

    public static void main(String[] args) throws Exception
    {
        // Hole %APPDATA% und speichert es in appdata
        String appdata = System.getenv("APPDATA");
        // System.out.println("appdata: "  + appdata);

        Path mcdir = Paths.get(appdata).resolve(".minecraft");
        // System.out.println("mc dir: "  + mcdir);

        boolean mcinstalled = mcdir.toFile().exists();
        // System.out.println("mcinstalled:" + mcinstalled);

        if (! mcinstalled)
        {
            System.err.println("Minecraft ist nicht installiert!");
            return;
        }

        Path mcversionDir = mcdir.resolve("versions");
        simpleCopy(Paths.get("assets", "pierremod"), mcversionDir);

        Path pierremodDir = mcdir.resolve("pierremod");
        File pierremodFile = pierremodDir.toFile();

        if (! pierremodFile.exists())
        {
            pierremodFile.mkdir();
        }

        simpleCopy(Paths.get("assets", "config"),    pierremodDir);
        simpleCopy(Paths.get("assets", "resources"), pierremodDir);
        simpleCopy(Paths.get("assets", "mods"),      pierremodDir);

        try {
            Files.copy(
                    mcdir.resolve("options.txt"),
                    pierremodDir.resolve("options.txt"),
                    StandardCopyOption.REPLACE_EXISTING
            );

            Files.copy(
                    mcdir.resolve("hotbar.dat"),
                    pierremodDir.resolve("hotbar.dat"),
                    StandardCopyOption.REPLACE_EXISTING
            );
        } catch (Exception exc)
        {

        }
    }
}
