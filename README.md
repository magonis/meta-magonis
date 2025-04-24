
# meta-magonis Yocto Layer

## Introduction

This Yocto Project layer, `meta-magonis`, provides recipes, configuration, and extensions tailored for Magonis devices based in Barcelona, Catalonia, Spain. It aims to integrate seamlessly with standard Yocto builds targeting Magonis hardware platforms.

This layer currently includes:
* Recipes for Magonis-specific applications (e.g., `can-log-replay`).
* Configuration files supporting the Magonis ecosystem.

## Dependencies

This layer depends on:

* **Poky (OpenEmbedded Core):** `URI: git://git.yoctoproject.org/poky`
    * Tested compatible branches/versions: `kirkstone`, `dunfell` (update as needed)
* **Other Layers:** (List any other required layers if applicable)

## How to Use This Layer

1.  **Clone the repository:**
    ```bash
    git clone [https://github.com/](https://github.com/)magonis/meta-magonis.git
    ```

2.  **Add the layer to your build:**
    * Ensure your `bblayers.conf` file (usually found in your Yocto build directory's `conf` folder) includes the path to this layer:
        ```diff
        BBLAYERS ?= " \\
          /path/to/poky/meta \\
          /path/to/poky/meta-poky \\
          /path/to/poky/meta-yocto-bsp \\
        + /path/to/meta-magonis \\
          "
        ```
        *(Adjust the paths according to your setup)*

3.  **Add recipes to your image:**
    * To include specific packages provided by this layer in your target image, add them to `IMAGE_INSTALL:append` in your `local.conf` or image recipe file. For example:
        ```conf
        # In conf/local.conf
        IMAGE_INSTALL:append = " can-log-replay"
        ```

## Included Recipes

### `can-log-replay`

* **Summary:** CAN Log Replay Application and Service.
* **Description:** This recipe builds and installs an application that reads CAN messages from a log file (`/opt/can_logs/replay.log`) and replays them onto the `vcan0` (virtual CAN) interface. It attempts to maintain the original timing between messages. It includes systemd services for automatically setting up the `vcan0` interface and running the replay application on boot.
* **Purpose:** Useful for testing CAN bus applications, simulation, or diagnostics without requiring physical CAN hardware during development or specific test phases.
* **Runtime Dependencies:** Requires `iproute2` and `kernel-module-vcan` on the target device.
* **Configuration:** Uses `/opt/can_logs/replay.log` and the `vcan0` interface.
* **Systemd Services:** `setup-vcan0.service`, `can-log-replay.service`.
* **Usage:** Starts automatically. Check status with `systemctl` and logs with `journalctl`. See `man can_log_replay` on the target.

## Maintainer & Contribution

* **Maintainer:** Magonis Team <info@magonis.com>
* **Bug Reports/Feature Requests:** Use the GitHub issue tracker for this repository: https://github.com/magonis/meta-magonis/issues
* **Contributions:** Pull requests are welcome!

---
*Generated for Magonis Devices - 2025-04-24*
