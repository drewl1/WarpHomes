# WarpHomes

This plugin allows players to set their homes, teleport to their set homes, delete their set homes, and access a GUI to manage their homes conveniently.

## Features

- Set a home location with a name
- Delete homes set by the player.
- Teleport to a home set by the player.
- GUI for managing homes.
- Optional choice to set a color for the bed icon in the GUI
  
## Commands

- `/sethome [name] <GUI bed color>`: Sets a home location with an optional name.
- `/delhome [name]`: Deletes a home location with the specified name.
- `/home [name]`: Teleports the player to the specified home location.
- `/homes`: Opens a GUI listing all the player's homes. Clicking on a listed home teleports the player to that location.

## Installation

1. Download the plugin JAR file from the [releases](https://github.com/drewl1/WarpHomes/releases) page.
2. Place the downloaded JAR file into the `plugins` folder of your Minecraft server directory.
3. Restart or reload your server to enable the plugin.

## Usage

1. To set a home, stand where you want to set the home and use the command `/sethome [name]`. You can supply an valid wool color after the name to set the bed coior in the GUI. See Notes for all options.
2. To delete a home, use the command `/delhome [name]`. 
3. To teleport to a home, use the command `/home [name]`.
4. To access the GUI listing all your homes, use the command `/homes`. Click on a listed home to teleport to that location. Shift+Right Click to delete a home.

## Permissions

- `sethome.x`: Replace 'x' with a number up to 54 (for now). 'x' represents how many homes a player can set.

## Notes
- Any colored bed color is valid, so any wool color.

  <details>
  <summary>Valid Colors</summary>
   white, orange, magenta, lightblue, yellow, lime, pink, gray, lightgray, cyan, purple, blue, brown, green, red, black
  </details>

- Currently, the max homes is 54 (size of a double chest). I don't feel like adding paginated GUIs now. next update probably but who needs 54+ homes?
  
## Support

If you encounter any issues or have suggestions for improvements, please [submit an issue](https://github.com/drewl1/WarpHomes/issues) on our GitHub repository.
<br>
If you need a specific feature, get in contact with me and I'll send you a version with whatever you want.

## License

This plugin is licensed under the MIT License. See the [LICENSE](LICENSE) file for more information.
