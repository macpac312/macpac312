// Get DOM elements
const toggle = document.getElementById('darkModeToggle');
const status = document.getElementById('status');

// Load saved state when popup opens
browser.storage.local.get(['darkModeEnabled']).then((result) => {
  const isEnabled = result.darkModeEnabled || false;
  toggle.checked = isEnabled;
  updateStatus(isEnabled);
});

// Handle toggle change
toggle.addEventListener('change', async () => {
  const isEnabled = toggle.checked;

  // Save state
  await browser.storage.local.set({ darkModeEnabled: isEnabled });

  // Update status text
  updateStatus(isEnabled);

  // Notify all tabs about the change
  const tabs = await browser.tabs.query({});
  tabs.forEach(tab => {
    browser.tabs.sendMessage(tab.id, {
      action: 'toggleDarkMode',
      enabled: isEnabled
    }).catch(() => {
      // Ignore errors for tabs where content script isn't loaded
    });
  });
});

function updateStatus(isEnabled) {
  status.textContent = isEnabled ? 'On' : 'Off';
}
