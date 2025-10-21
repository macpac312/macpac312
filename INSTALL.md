# Installation Guide - Dark Mode Toggle Extension

## Quick Installation (Temporary - For Testing)

1. **Open Firefox** and navigate to: `about:debugging`

2. **Click** "This Firefox" in the left sidebar

3. **Click** "Load Temporary Add-on..." button

4. **Navigate** to the extension folder and select the `manifest.json` file:
   ```
   /home/user/macpac312/manifest.json
   ```

5. The extension should now appear in your toolbar!

## Permanent Installation

### Option 1: Package as XPI

1. **Zip the extension files:**
   ```bash
   cd /home/user/macpac312
   zip -r -FS dark-mode-toggle.xpi * -x "*.git*" "*.md" "*.sh" "create-*.py"
   ```

2. **Install the XPI:**
   - In Firefox, go to `about:addons`
   - Click the gear icon ⚙️
   - Select "Install Add-on From File..."
   - Choose the `dark-mode-toggle.xpi` file

### Option 2: Firefox Developer Edition

For development and testing with auto-reload:

```bash
# Install web-ext if not already installed
npm install -g web-ext

# Run the extension with auto-reload
cd /home/user/macpac312
web-ext run
```

## Troubleshooting

### "This add-on could not be installed because it appears to be corrupt"

This error can occur due to:

1. **Invalid icon files** - Fixed by using proper RGBA PNG icons
2. **File permission issues** - Make sure all files are readable
3. **Path issues** - Ensure you're selecting the manifest.json file, not the folder

**Solution:**
- Make sure you're selecting the `manifest.json` file directly when loading
- Check that all icon files exist: `ls -la icons/*.png`
- Verify manifest.json is valid JSON: `cat manifest.json | python3 -m json.tool`

### Extension loads but doesn't work

1. **Check browser console** - Right-click the extension icon → Inspect
2. **Check permissions** - The extension needs `storage`, `activeTab`, `tabs`, and `<all_urls>` permissions
3. **Reload the extension** - Go to `about:debugging` and click Reload

### Dark mode doesn't apply

1. Click the extension icon to open the popup
2. Toggle the switch to ON
3. Refresh the webpage
4. Some sites with strong CSS may override the dark mode

## Usage

Once installed:

1. **Click** the extension icon in the Firefox toolbar
2. **Toggle** the switch to turn dark mode ON or OFF
3. All websites will immediately apply the dark mode filter
4. Your preference is saved automatically

## Uninstallation

1. Go to `about:addons`
2. Find "Dark Mode Toggle" in the list
3. Click the three dots menu (...)
4. Select "Remove"

## Support

If you continue to have issues:
- Check Firefox version (should be 48+)
- Try restarting Firefox
- Check the browser console for error messages
