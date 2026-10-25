const fs = require('fs');
const path = require('path');

const filePath = '../src/main/resources/assets/alexsmobs/models/item';

const itemModels = fs.readdirSync(filePath);

let datagenLines = [];

String.prototype.toProperCase = function() {
    return this[0].toUpperCase() + this.substring(1).toLowerCase();
}

function toCamelCase(name) {
    let split = name.split('_').map(e => e.toProperCase());
    split[0] = split[0].toLowerCase();

    return split.join('');
}

for (const file of itemModels) {
    const data = JSON.parse(fs.readFileSync(path.resolve(filePath, file)).toString());
    const baseId = file.replace('.json', '');

    if (data.parent == 'minecraft:item/generated' && data.textures.layer0 == 'alexsmobs:item/' + baseId && !data.display) {
        datagenLines.push(`basicItem(AMItemRegistry.${baseId.toUpperCase()}.asItem());`);
        fs.rmSync(path.resolve(filePath, file));
    } else if (data.parent == 'minecraft:item/handheld' && data.textures.layer0 == 'alexsmobs:item/' + baseId && !data.display) {
        datagenLines.push(`handheldItem(AMItemRegistry.${baseId.toUpperCase()}.asItem());`);
        fs.rmSync(path.resolve(filePath, file));
    } else if (!data.display && data.parent == 'alexsmobs:block/' + baseId) {
        datagenLines.push(`simpleBlockItem(AMBlockRegistry.${baseId.toUpperCase()}.value());`);
        fs.rmSync(path.resolve(filePath, file));
    }
}

fs.writeFileSync('./datagen.java', datagenLines.join('\r\n'));
