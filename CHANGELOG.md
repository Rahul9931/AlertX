# Changelog

All notable changes to this project will be documented in this file.

## [1.0.0] - 2026-06-07

### Added
- Initial official release of **AlertX** library.
- **AlertXToast**: Lightweight notifications for brief messages.
- **AlertXSheet**: Prominent alert sheets for interactive messages.
- **Dual Positioning**: Support for showing alerts at both **TOP** and **BOTTOM** of the screen.
- **Rich Animations**:
    - `MORPH_FROM_BALL` (Ball-to-Rectangle transformation).
    - `SLIDE_FROM_VERTICAL` & `SLIDE_FROM_VERTICAL_BOUNCE`.
    - `SLIDE_FROM_LEFT` & `SLIDE_FROM_RIGHT` with bounce effects.
    - `ZOOM` and `FADE` effects.
- **Queue Management**: Built-in system to handle multiple alerts sequentially without overlapping.
- **Global Configuration**: Ability to set default styles (colors, duration, animations) globally in the Application class.
- **Lifecycle Awareness**: Implemented `WeakReference` and lifecycle safety to prevent memory leaks and crashes on null activities.
- **Interactive UI**: Support for swipe-to-dismiss and custom icons.
- **Standard Types**: Pre-styled Success, Info, Warning, and Error types.

### Changed
- Refactored `AlertXTop` to `AlertXSheet` for better naming consistency.
- Updated Toast UI for a more modern look and feel.
- Optimized animation structures for smoother transitions.

### Fixed
- Resolved manifest merge issues for library consumers.
- Fixed activity-null related crashes and memory leaks.
- Resolved color override issues where theme colors were conflicting with library styles.
- Fixed queue management issues where alerts would sometimes overlap or fail to dismiss.
- Fixed XML parsing errors in layout files.
