package com.comphenix.protocol.utility;

import net.minecraft.server.v1_12_R1.EntityPlayer;
import org.bukkit.entity.Player;

import com.comphenix.protocol.injector.BukkitUnwrapper;
import com.comphenix.protocol.reflect.accessors.Accessors;
import com.comphenix.protocol.reflect.accessors.FieldAccessor;
import com.google.common.base.Preconditions;

/**
 * Retrieve the content of well-known fields in Minecraft.
 * @author Kristian
 */
public class MinecraftFields {
	// Cached accessors
	private static volatile FieldAccessor CONNECTION_ACCESSOR;
	private static volatile FieldAccessor NETWORK_ACCESSOR;
	private static volatile FieldAccessor CONNECTION_ENTITY_ACCESSOR;

	private MinecraftFields() {
		// Not constructable
	}
	
	/**
	 * Retrieve the network manager associated with a particular player.
	 * @param player - the player.
	 * @return The network manager, or NULL if no network manager has been associated yet.
	 */
	public static Object getNetworkManager(Player player) {
		EntityPlayer nmsPlayer = BukkitUnwrapper.getInstance().unwrapItem(player); // Solar
		
		if (NETWORK_ACCESSOR == null) {
			Class<?> networkClass = MinecraftReflection.getNetworkManagerClass();
			Class<?> connectionClass = MinecraftReflection.getPlayerConnectionClass();
			NETWORK_ACCESSOR = Accessors.getFieldAccessor(connectionClass, networkClass, true);
		}
		// Retrieve the network manager
		final Object playerConnection = getPlayerConnection(nmsPlayer);
		
		if (playerConnection != null)
			return NETWORK_ACCESSOR.get(playerConnection);
		return null;
	}
	
	/**
	 * Retrieve the PlayerConnection (or NetServerHandler) associated with a player.
	 * @param player - the player.
	 * @return The player connection.
	 */
	public static Object getPlayerConnection(Player player) {
		Preconditions.checkNotNull(player, "player cannot be null!");
		return getPlayerConnection(BukkitUnwrapper.getInstance().unwrapItem(player));
	}

	/**
	 * Retrieve the PlayerConnection (or NetServerHandler) associated with a player.
	 * @param nmsPlayer - the NMS player.
	 * @return The player connection.
	 */
	public static Object getPlayerConnection(EntityPlayer nmsPlayer) { // Solar - use EntityPlayer
		Preconditions.checkNotNull(nmsPlayer, "nmsPlayer cannot be null!");

// Solar start
		return nmsPlayer.playerConnection; /*
		if (CONNECTION_ACCESSOR == null) {
			Class<?> connectionClass = MinecraftReflection.getPlayerConnectionClass();
			CONNECTION_ACCESSOR = Accessors.getFieldAccessor(nmsPlayer.getClass(), connectionClass, true);
		}
		return CONNECTION_ACCESSOR.get(nmsPlayer);
*/ // Solar end
	}

	/**
	 * Retrieves the EntityPlayer player field from a PlayerConnection.
	 *
	 * @param playerConnection The PlayerConnection object from which to retrieve the EntityPlayer field.
	 * @return The value of the EntityPlayer field in the PlayerConnection.
	 */
	public static Object getPlayerFromConnection(Object playerConnection) {
		Preconditions.checkNotNull(playerConnection, "playerConnection cannot be null!");

		if (CONNECTION_ENTITY_ACCESSOR == null) {
			Class<?> connectionClass = MinecraftReflection.getPlayerConnectionClass();
			Class<?> entityPlayerClass = MinecraftReflection.getEntityPlayerClass();
			CONNECTION_ENTITY_ACCESSOR = Accessors.getFieldAccessor(connectionClass, entityPlayerClass, true);
		}
		return CONNECTION_ENTITY_ACCESSOR.get(playerConnection);
	}
}
