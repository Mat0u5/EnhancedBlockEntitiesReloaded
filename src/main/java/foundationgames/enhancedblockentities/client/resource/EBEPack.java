package foundationgames.enhancedblockentities.client.resource;

import foundationgames.enhancedblockentities.util.EBEUtil;
import foundationgames.enhancedblockentities.client.resource.template.TemplateLoader;
import foundationgames.enhancedblockentities.client.resource.template.TemplateProvider;
import net.minecraft.SharedConstants;
//? if <= 1.19.2 {
/*import java.io.FileNotFoundException;
import java.util.function.Predicate;
*///?} else {
import net.minecraft.client.renderer.texture.atlas.SpriteSource;
import net.minecraft.client.renderer.texture.atlas.sources.DirectoryLister;
import net.minecraft.client.renderer.texture.atlas.sources.SingleFile;
import net.minecraft.server.packs.BuiltInMetadata;
import net.minecraft.server.packs.resources.IoSupplier;
//?}
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
//? if >= 1.21 {
import net.minecraft.server.packs.PackLocationInfo;
//?}
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.metadata.MetadataSectionSerializer;
import net.minecraft.server.packs.metadata.pack.PackMetadataSection;
import net.minecraft.server.packs.repository.PackSource;
import org.jetbrains.annotations.Nullable;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class EBEPack implements PackResources {
    public static final ResourceLocation BLOCK_ATLAS = EBEUtil.rl("blocks");

    //? if >= 1.19.4 {
    private final Map<ResourceLocation, AtlasResourceBuilder> atlases = new HashMap<>();
    //?}
    private final Map<ResourceLocation, IoSupplier<InputStream>> resources = new HashMap<>();
    private final Set<String> namespaces = new HashSet<>();

    private final TemplateLoader templates;

    private final PackMetadataSection packMeta;
    //? if <= 1.20 {
    /*private final String packInfo;
    *///?} else {
    private final PackLocationInfo packInfo;
    //?}

    public EBEPack(ResourceLocation id, TemplateLoader templates) {
        this.templates = templates;

        //? if <= 1.16 {
        /*this.packMeta = new PackMetadataSection(
                EBEUtil.text("Enhanced Block Entities Resources"),
                SharedConstants.getCurrentVersion().getPackVersion());

        this.packInfo = id.toString();
        *///?} else if <= 1.19.2 {
        /*this.packMeta = new PackMetadataSection(
                EBEUtil.text("Enhanced Block Entities Resources"),
                SharedConstants.getCurrentVersion().getPackVersion(com.mojang.bridge.game.PackType.RESOURCE));

        this.packInfo = id.toString();
        *///?} else if <= 1.20 {
        /*this.packMeta = new PackMetadataSection(
                EBEUtil.text("Enhanced Block Entities Resources"),
                SharedConstants.getCurrentVersion().getPackVersion(PackType.CLIENT_RESOURCES));

        this.packInfo = id.toString();
        *///?} else {
        this.packMeta = new PackMetadataSection(
                EBEUtil.text("Enhanced Block Entities Resources"),
                SharedConstants.getCurrentVersion().getPackVersion(PackType.CLIENT_RESOURCES),
                Optional.empty());

        this.packInfo = new PackLocationInfo(id.toString(), EBEUtil.text(id.toString()), PackSource.BUILT_IN, Optional.empty());
        //?}
    }

    //? if >= 1.19.4 {
    public void addAtlasSprite(ResourceLocation atlas, SpriteSource source) {
        var resource = this.atlases.computeIfAbsent(atlas, id -> new AtlasResourceBuilder());
        resource.put(source);

        this.addResource(EBEUtil.rl(atlas.getNamespace(), "atlases/" + atlas.getPath() + ".json"), resource::toBytes);
    }
    //?}

    public void addSingleBlockSprite(ResourceLocation path) {
        //? if >= 1.19.4 {
        this.addAtlasSprite(BLOCK_ATLAS, new SingleFile(path, Optional.empty()));
        //?}
    }

    public void addDirBlockSprites(String dir, String prefix) {
        //? if >= 1.19.4 {
        this.addAtlasSprite(BLOCK_ATLAS, new DirectoryLister(dir, prefix));
        //?}
    }

    public void addResource(ResourceLocation id, IoSupplier<byte[]> resource) {
        this.namespaces.add(id.getNamespace());
        this.resources.put(id, new LazyBufferedResource(resource));
    }

    public void addResource(ResourceLocation id, byte[] resource) {
        this.namespaces.add(id.getNamespace());
        this.resources.put(id, () -> new ByteArrayInputStream(resource));
    }

    public void addPlainTextResource(ResourceLocation id, String plainText) {
        this.addResource(id, plainText.getBytes(StandardCharsets.UTF_8));
    }

    public void addTemplateResource(ResourceLocation id, TemplateProvider.TemplateApplyingFunction template) {
        this.addResource(id, () -> template.getAndApplyTemplate(new TemplateProvider(this.templates)).getBytes(StandardCharsets.UTF_8));
    }

    public void addTemplateResource(ResourceLocation id, String templatePath) {
        this.addTemplateResource(id, t -> t.load(templatePath, d -> {}));
    }

    //? if <= 1.19.2 {
    /*@Override
    public InputStream getRootResource(String fileName) throws IOException {
        throw new FileNotFoundException(fileName);
    }

    @Override
    public InputStream getResource(PackType type, ResourceLocation id) throws IOException {
        var resource = type == PackType.CLIENT_RESOURCES ? this.resources.get(id) : null;

        if (resource == null) {
            throw new FileNotFoundException(id.toString());
        }

        return resource.get();
    }

    //? if <= 1.18 {
    /^@Override
    public Collection<ResourceLocation> getResources(PackType type, String namespace, String prefix, int maxDepth, Predicate<String> filter) {
        if (type != PackType.CLIENT_RESOURCES) return List.of();

        var found = new ArrayList<ResourceLocation>();

        for (var id : this.resources.keySet()) {
            if (!id.getNamespace().equals(namespace) || !id.getPath().startsWith(prefix)) continue;

            var path = id.getPath();
            var cut = path.lastIndexOf('/');

            if (depthBelow(path, prefix) > maxDepth) continue;

            if (filter.test(path.substring(cut + 1))) {
                found.add(id);
            }
        }

        return found;
    }

    private static int depthBelow(String path, String prefix) {
        int depth = 0;

        for (int i = prefix.length(); i < path.length(); i++) {
            if (path.charAt(i) == '/') depth++;
        }

        return depth;
    }
    ^///?} else {
    @Override
    public Collection<ResourceLocation> getResources(PackType type, String namespace, String prefix, Predicate<ResourceLocation> filter) {
        if (type != PackType.CLIENT_RESOURCES) return List.of();

        var found = new ArrayList<ResourceLocation>();

        for (var id : this.resources.keySet()) {
            if (id.getNamespace().equals(namespace) && id.getPath().startsWith(prefix) && filter.test(id)) {
                found.add(id);
            }
        }

        return found;
    }
    //?}

    @Override
    public boolean hasResource(PackType type, ResourceLocation id) {
        return type == PackType.CLIENT_RESOURCES && this.resources.containsKey(id);
    }
    *///?} else {
    @Nullable
    @Override
    public IoSupplier<InputStream> getRootResource(String... segments) {
        return null;
    }

    @Nullable
    @Override
    public IoSupplier<InputStream> getResource(PackType type, ResourceLocation id) {
        if (type != PackType.CLIENT_RESOURCES) return null;

        return this.resources.get(id);
    }

    @Override
    public void listResources(PackType type, String namespace, String prefix, ResourceOutput consumer) {
        if (type != PackType.CLIENT_RESOURCES) return;

        for (var entry : this.resources.entrySet()) {
            var id = entry.getKey();

            if (id.getNamespace().startsWith(namespace) && id.getPath().startsWith(prefix)) {
                consumer.accept(id, entry.getValue());
            }
        }
    }
    //?}

    @Override
    public Set<String> getNamespaces(PackType type) {
        if (type != PackType.CLIENT_RESOURCES) return Set.of();

        return this.namespaces;
    }

    @Nullable
    @Override
    @SuppressWarnings("unchecked")
    public <T> T getMetadataSection(MetadataSectionSerializer<T> meta) {
        //? if <= 1.19.2 {
        /*return "pack".equals(meta.getMetadataSectionName()) ? (T) this.packMeta : null;
        *///?} else {
        return BuiltInMetadata.of(PackMetadataSection.TYPE, this.packMeta).get(meta);
        //?}
    }

    //? if <= 1.19.2 {
    /*@Override
    public String getName() {
        return this.packInfo;
    }
    *///?} else if <= 1.20 {
    /*@Override
    public String packId() {
        return this.packInfo;
    }
    *///?} else {
    @Override
    public PackLocationInfo location() {
        return this.packInfo;
    }
    //?}

    @Override
    public void close() {
    }

    public void dump(Path dir) throws IOException {
        dir = dir.resolve("assets");

        for (var entry : this.resources.entrySet()) {
            var id = entry.getKey();
            var file = dir.resolve(id.getNamespace()).resolve(id.getPath());

            Files.createDirectories(file.getParent());

            try (var out = Files.newOutputStream(file)) {
                var in = entry.getValue().get();

                int i;
                while ((i = in.read()) >= 0) {
                    out.write(i);
                }
            }
        }
    }

    //? if <= 1.19.2 {
    /*public interface IoSupplier<T> {
        T get() throws IOException;
    }
    *///?}

    public static class PropertyBuilder {
        private Properties properties = new Properties();

        private PropertyBuilder() {}

        public PropertyBuilder def(String k, String v) {
            if (this.properties != null) {
                this.properties.setProperty(k, v);
            }

            return this;
        }

        private Properties build() {
            var properties = this.properties;
            this.properties = null;

            return properties;
        }
    }

    public static class LazyBufferedResource implements IoSupplier<InputStream> {
        private final IoSupplier<byte[]> backing;
        private byte[] buffer = null;

        public LazyBufferedResource(IoSupplier<byte[]> backing) {
            this.backing = backing;
        }

        @Override
        public InputStream get() throws IOException {
            if (buffer == null) {
                buffer = backing.get();
            }

            return new ByteArrayInputStream(buffer);
        }
    }
}
