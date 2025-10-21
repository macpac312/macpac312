// Initialize default state on installation
browser.runtime.onInstalled.addListener(() => {
  browser.storage.local.get(['darkModeEnabled']).then((result) => {
    if (result.darkModeEnabled === undefined) {
      browser.storage.local.set({ darkModeEnabled: false });
    }
  });
});

// Listen for tab updates to inject dark mode on new pages
browser.tabs.onUpdated.addListener((tabId, changeInfo, tab) => {
  if (changeInfo.status === 'loading') {
    browser.storage.local.get(['darkModeEnabled']).then((result) => {
      if (result.darkModeEnabled) {
        // The content script will handle applying dark mode
        // This is just for state management
      }
    });
  }
});
