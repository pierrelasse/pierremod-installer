import java.nio.file.*;

public class Main
{
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

        Files.walk(Paths.get("assets", "pierremod")).forEach(source ->
        {
            Path destPath = source.subpath(1, source.getNameCount());
            Path destination = mcversionDir.resolve(destPath);

            System.out.println("Copy: " + source + " --> " + destination);

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
}
