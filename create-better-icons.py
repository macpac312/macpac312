#!/usr/bin/env python3
"""
Create better quality PNG icons using proper PNG encoding.
"""

def create_better_png(width, height, r, g, b):
    """Create a properly formatted PNG with RGBA support."""
    import struct
    import zlib

    # PNG signature
    png = b'\x89PNG\r\n\x1a\n'

    def chunk(ctype, data):
        """Create a PNG chunk with CRC."""
        crc = zlib.crc32(ctype + data) & 0xffffffff
        return struct.pack(">I", len(data)) + ctype + data + struct.pack(">I", crc)

    # IHDR chunk - width, height, bit depth, color type, compression, filter, interlace
    # Color type 6 = RGBA (each pixel has red, green, blue, alpha)
    ihdr_data = struct.pack(">IIBBBBB", width, height, 8, 6, 0, 0, 0)
    png += chunk(b'IHDR', ihdr_data)

    # Create a simple circular icon
    import math
    raw = b''
    center_x, center_y = width / 2, height / 2
    radius = min(width, height) / 2 - 2

    for y in range(height):
        raw += b'\x00'  # Filter byte
        for x in range(width):
            # Calculate distance from center
            dx = x - center_x
            dy = y - center_y
            dist = math.sqrt(dx*dx + dy*dy)

            # Create a circle with anti-aliasing
            if dist <= radius - 1:
                # Inside circle - solid color
                raw += bytes([r, g, b, 255])
            elif dist <= radius + 1:
                # Edge - anti-aliased
                alpha = int(255 * (1 - (dist - radius + 1) / 2))
                raw += bytes([r, g, b, alpha])
            else:
                # Outside circle - transparent
                raw += bytes([0, 0, 0, 0])

    # IDAT chunk
    png += chunk(b'IDAT', zlib.compress(raw, 9))

    # IEND chunk
    png += chunk(b'IEND', b'')

    return png

# Purple color matching the theme
r, g, b = 102, 126, 234

sizes = [16, 32, 48, 96]

for size in sizes:
    png_data = create_better_png(size, size, r, g, b)
    filename = f'icons/icon{size}.png'
    with open(filename, 'wb') as f:
        f.write(png_data)
    print(f'Created {filename} ({size}x{size})')

print('\nIcons created successfully with proper RGBA encoding!')
