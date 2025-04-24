
# Recipe to build and install the Magonis CAN Log Replay application and services

SUMMARY = "Magonis CAN Log Replay Application and Service"
DESCRIPTION = "Replays CAN messages from a log file to vcan0. Includes systemd services for automatic setup and execution."
HOMEPAGE = "https://github.com/magonis/meta-magonis"
BUGTRACKER = "https://github.com/magonis/meta-magonis/issues"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

# --- Source Files ---
# Fetch source code, Makefile, systemd units, and man page from the 'files' subdirectory
# NOTE: Removed trailing backslashes from original SRC_URI lines
SRC_URI = " \\
    file://can_replay.c \\
    file://Makefile \\
    file://can-log-replay.service \\
    file://setup-vcan0.service \\
    file://can_log_replay.1 \\
    "
# Add this line if you want to package a default log file:
# SRC_URI += "file://replay.log"

S = "${WORKDIR}" # Source files are unpacked directly into WORKDIR

# --- Dependencies ---
# Runtime dependencies on the target device:
RDEPENDS:${PN} += "iproute2 kernel-module-vcan"

# --- Build Configuration ---
# Inherit systemd class for service management and make class for build
inherit systemd make

# --- Compilation Task (Uses the Makefile) ---
# oe_runmake uses the Makefile in ${S} and respects build environment variables.
do_compile() {
    oe_runmake
}

# --- Installation Task ---
do_install() {
    # Install the compiled executable (built as 'can_replay') to standard binary location,
    # renaming it to 'can_log_replay'.
    install -d ${D}${bindir}
    install -m 0755 ${B}/can_replay ${D}${bindir}/can_log_replay

    # Install the systemd service files from WORKDIR to the system unit directory
    install -d ${D}${systemd_system_unitdir}
    install -m 0644 ${WORKDIR}/can-log-replay.service ${D}${systemd_system_unitdir}/
    install -m 0644 ${WORKDIR}/setup-vcan0.service ${D}${systemd_system_unitdir}/

    # Install the man page
    install -d ${D}${mandir}/man1
    install -m 0644 ${WORKDIR}/can_log_replay.1 ${D}${mandir}/man1/

    # Create the log directory (doesn't install the log file by default)
    install -d ${D}/opt/can_logs

    # Optional: Install the default log file if included in SRC_URI
    # if [ -f "${WORKDIR}/replay.log" ]; then
    #     install -m 0644 ${WORKDIR}/replay.log ${D}/opt/can_logs/replay.log
    # fi
}

# --- Systemd Configuration ---
# List the services to be enabled by default on the target
SYSTEMD_SERVICE:${PN} = "setup-vcan0.service can-log-replay.service"

# --- Packaging ---
# Define the files to be included in the final package
# NOTE: Removed trailing backslashes from original FILES lines
FILES:${PN} += " \\
    ${bindir}/can_log_replay \\
    ${systemd_system_unitdir}/can-log-replay.service \\
    ${systemd_system_unitdir}/setup-vcan0.service \\
    ${mandir}/man1/can_log_replay.1 \\
    /opt/can_logs \\
"
# Add this if installing the default log file:
# FILES:${PN} += "/opt/can_logs/replay.log"

