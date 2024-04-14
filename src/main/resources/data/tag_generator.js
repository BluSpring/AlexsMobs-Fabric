const path = require('path');
const fs = require('fs/promises');
const fss = require('fs');

(async () => {
    const worldgenTags = await fs.readdir('./forge/tags/worldgen/biome', {recursive: true});

    for (const tag of worldgenTags) {
        const dir = path.dirname(`./c/tags/worldgen/biome/${tag}`);

        if (!tag.endsWith('.json'))
            continue;

        if (!fss.existsSync(dir))
            await fs.mkdir(dir, { recursive: true });

        await fs.writeFile(`./c/tags/worldgen/biome/${tag}`, JSON.stringify({
            replace: false,
            values: [
                {
                    id: `#forge:${tag.replace(/\\/g, '/').replace('.json', '')}`,
                    required: false
                }
            ]
        }, null, 4));
        console.log(`#forge:${tag} <-> #c:${tag}`);

        let original = JSON.parse(await fs.readFile(`./forge/tags/worldgen/biome/${tag}`));
        original.values.push({
            id: `#c:${tag.replace(/\\/g, '/').replace('.json', '')}`,
            required: false
        });

        await fs.writeFile(`./forge/tags/worldgen/biome/${tag}`, JSON.stringify(original, null, 4));
    }
})();