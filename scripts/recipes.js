const fs = require('fs');
const path = require('path');

const filePath = '../src/main/resources/data/alexsmobs/recipe';

const recipes = fs.readdirSync(filePath);

let datagenLines = [];

String.prototype.toProperCase = function() {
    return this[0].toUpperCase() + this.substring(1).toLowerCase();
}

function toCamelCase(name) {
    let split = name.split('_').map(e => e.toProperCase());
    split[0] = split[0].toLowerCase();

    return split.join('');
}

function determineItemSource(id) {
    const split = id.split(':');
    if (split.length == 1 || split[0] == 'minecraft')
        return `Items.${split[1].toUpperCase()}`;
    else if (split[0] == 'alexsmobs')
        return `AMItemRegistry.${split[1].toUpperCase()}`;
    else
        throw `bruh ${id}`;
}

function determineTagSource(id) {
    if (id.startsWith('#'))
        id = id.substring(1);

    const split = id.split(':');
    if (split.length == 1 || split[0] == 'minecraft')
        return `ItemTags.${split[1].toUpperCase()}`;
    else if (split[0] == 'alexsmobs')
        return `AMTagRegistry.${split[1].toUpperCase()}`;
    else if (split[0] == 'c')
        return `Tags.Items.${split[1].toUpperCase()}`;
    else
        throw `bruh ${id}`;
}

for (const file of recipes) {
    const data = JSON.parse(fs.readFileSync(path.resolve(filePath, file)).toString());
    const baseId = file.replace('.json', '');

    if (data.type == 'minecraft:crafting_shapeless') {
        datagenLines.push(`ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ${determineItemSource(data.result.id)}, ${data.result.count ?? 1})`);
        for (const ingredient of data.ingredients) {
            if (typeof ingredient == 'string')
                if (!ingredient.startsWith('#'))
                    datagenLines.push(`  .requires(${determineItemSource(ingredient)})`);
                else
                    datagenLines.push(`  .requires(${determineTagSource(ingredient)})`);
            else if (ingredient.item)
                datagenLines.push(`  .requires(${determineItemSource(ingredient.item)})`);
            else if (ingredient.tag)
                datagenLines.push(`  .requires(${determineTagSource(ingredient.tag)})`);
            else
                throw ingredient;
        }

        datagenLines.push(`  .unlockedBy(getHasName(AMItemRegistry.), has(AMItemRegistry.))`);
        datagenLines.push(`  .save(output, AlexsMobs.prefix("${baseId}"));`);
        datagenLines.push('');
        fs.rmSync(path.resolve(filePath, file));
    } else if (data.type == 'minecraft:crafting_shaped') {
        datagenLines.push(`ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ${determineItemSource(data.result.id)}, ${data.result.count ?? 1})`);
        for (const pattern of data.pattern) {
            datagenLines.push(`  .pattern("${pattern}")`);
        }

        for (const key of Object.keys(data.key)) {
            const ingredient = data.key[key];
            if (typeof ingredient == 'string') {
                if (!ingredient.startsWith('#'))
                    datagenLines.push(`  .define('${key}', ${determineItemSource(ingredient)})`);
                else
                    datagenLines.push(`  .define('${key}', ${determineTagSource(ingredient)})`);
            } else {
                datagenLines.push(`  .define('${key}', ${ingredient.item ? determineItemSource(ingredient.item) : determineTagSource(ingredient.tag)})`);
            }
        }

        datagenLines.push(`  .unlockedBy(getHasName(AMItemRegistry.), has(AMItemRegistry.))`);
        datagenLines.push(`  .save(output, AlexsMobs.prefix("${baseId}"));`);
        datagenLines.push('');
        fs.rmSync(path.resolve(filePath, file));
    }
}

fs.writeFileSync('./datagen.java', datagenLines.join('\r\n'));
