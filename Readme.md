# ProtocolLib

Certain tasks are impossible to perform with the standard Bukkit API, and may require
working with and even modify Minecraft directly. A common technique is to modify incoming
and outgoing [packets](https://www.wiki.vg/Protocol), or inject custom packets into the
stream. This is quite cumbersome to do, however, and most implementations will break
as soon as a new version of Minecraft has been released, mostly due to obfuscation.

Critically, different plugins that use this approach may _hook_ into the same classes,
with unpredictable outcomes. More than often this causes plugins to crash, but it may also
lead to more subtle bugs.

## Fork Status and Licensing

The original ProtocolLib is licensed under the GNU General Public License, v2 or later. Modifications made
in this fork are licensed under the GNU General Public License, v3 or later. As such, the whole of the work
must be conveyed according to the GNU General Public License, v3, or at your discretion, any later version.
*This is not legal advice* - please consult the license file for the precise licensing.

