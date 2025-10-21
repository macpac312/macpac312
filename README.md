# Dark Mode Toggle - Firefox Extension

A simple Firefox extension that allows you to toggle dark mode on/off for all websites with a single click.

## Features

- Toggle dark mode on/off with a beautiful popup interface
- Applies dark mode to all websites
- Remembers your preference across browser sessions
- Clean, modern UI with gradient design
- Smooth transitions and animations

## Installation

### Method 1: Temporary Installation (for testing)

1. Open Firefox and navigate to `about:debugging`
2. Click "This Firefox" in the left sidebar
3. Click "Load Temporary Add-on"
4. Navigate to this extension's directory and select the `manifest.json` file
5. The extension will now be loaded and active

### Method 2: Permanent Installation

1. Create the icon files (see "Creating Icons" section below)
2. Zip all the extension files together
3. Navigate to `about:addons` in Firefox
4. Click the gear icon and select "Install Add-on From File"
5. Select the zip file you created

## Creating Icons

Before installing the extension permanently, you need to create the icon files. You have several options:

### Option 1: Use the provided script

If you have `rsvg-convert` or ImageMagick installed:

```bash
./create-icons.sh
```

Install the required tools:
- Ubuntu/Debian: `sudo apt-get install librsvg2-bin`
- macOS: `brew install librsvg`

### Option 2: Online SVG to PNG converter

1. Go to an online converter like https://cloudconvert.com/svg-to-png
2. Upload `icons/icon.svg`
3. Convert to PNG at these sizes: 16x16, 32x32, 48x48, 96x96
4. Save them as `icon16.png`, `icon32.png`, `icon48.png`, `icon96.png` in the `icons/` directory

### Option 3: Create your own icons

Create PNG files at sizes 16x16, 32x32, 48x48, and 96x96 pixels and save them in the `icons/` directory.

## Usage

1. Click the extension icon in the Firefox toolbar
2. Toggle the switch to turn dark mode on or off
3. All websites will immediately apply or remove the dark mode filter
4. Your preference is saved automatically

## How It Works

The extension uses CSS filters to invert colors and rotate hues, creating a dark mode effect:

- Inverts the main page colors
- Applies a counter-invert to images and videos to keep them looking normal
- Uses browser storage to remember your preference
- Content scripts inject the dark mode CSS into every page

## Files Structure

```
firefox-dark-mode/
├── manifest.json              # Extension configuration
├── popup/
│   ├── popup.html            # Popup interface
│   ├── popup.css             # Popup styling
│   └── popup.js              # Popup logic
├── content/
│   └── content.js            # Content script for applying dark mode
├── background/
│   └── background.js         # Background script for state management
├── icons/
│   ├── icon.svg              # Source SVG icon
│   ├── icon16.png            # 16x16 icon (needs to be generated)
│   ├── icon32.png            # 32x32 icon (needs to be generated)
│   ├── icon48.png            # 48x48 icon (needs to be generated)
│   └── icon96.png            # 96x96 icon (needs to be generated)
├── create-icons.sh           # Script to generate icons
└── README.md                 # This file
```

## Customization

You can customize the dark mode filter by editing `content/content.js`. The current filter settings are:

```css
filter: invert(0.9) hue-rotate(180deg);
```

Adjust these values to change the darkness level and color balance:
- `invert(0.9)` - Controls how much to invert (0.9 = 90% inverted)
- `hue-rotate(180deg)` - Rotates colors to maintain natural appearance

## Browser Compatibility

This extension is designed for Firefox and uses the WebExtensions API. It should work on:
- Firefox 48+
- Firefox for Android 48+

## Permissions

The extension requires the following permissions:
- `storage` - To save your dark mode preference
- `activeTab` - To apply dark mode to the current tab
- `tabs` - To apply dark mode to all tabs
- `<all_urls>` - To inject dark mode CSS into all websites

## Privacy

This extension:
- Does NOT collect any data
- Does NOT track your browsing
- Does NOT send any information to external servers
- Only stores your dark mode preference locally in your browser

## License

Free to use and modify.

## Troubleshooting

**Dark mode doesn't apply to a specific site:**
Some websites may override the dark mode with their own styles. Try refreshing the page after enabling dark mode.

**Extension icon doesn't appear:**
Make sure you've created the icon PNG files as described in the "Creating Icons" section.

**Dark mode looks wrong on some sites:**
You can adjust the filter settings in `content/content.js` to better suit your preferences.
