// Dark mode CSS that will be injected
const darkModeCSS = `
  html {
    filter: invert(0.9) hue-rotate(180deg) !important;
    background-color: #1a1a1a !important;
  }

  img, video, iframe, [style*="background-image"] {
    filter: invert(1) hue-rotate(180deg) !important;
  }

  * {
    background-color: inherit !important;
    scrollbar-color: #454a4d #202324 !important;
  }
`;

// Create or get style element
let styleElement = null;

function createStyleElement() {
  styleElement = document.createElement('style');
  styleElement.id = 'dark-mode-extension-style';
  styleElement.textContent = darkModeCSS;
  return styleElement;
}

function applyDarkMode() {
  if (!styleElement) {
    styleElement = createStyleElement();
  }

  if (!document.head) {
    // If document.head doesn't exist yet, wait for it
    const observer = new MutationObserver(() => {
      if (document.head) {
        document.head.appendChild(styleElement);
        observer.disconnect();
      }
    });
    observer.observe(document.documentElement, { childList: true });
  } else {
    document.head.appendChild(styleElement);
  }
}

function removeDarkMode() {
  if (styleElement && styleElement.parentNode) {
    styleElement.parentNode.removeChild(styleElement);
  }
}

// Initialize on page load
browser.storage.local.get(['darkModeEnabled']).then((result) => {
  if (result.darkModeEnabled) {
    applyDarkMode();
  }
});

// Listen for messages from popup
browser.runtime.onMessage.addListener((message) => {
  if (message.action === 'toggleDarkMode') {
    if (message.enabled) {
      applyDarkMode();
    } else {
      removeDarkMode();
    }
  }
});
