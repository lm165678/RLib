package rlib.network.packet.impl;

import java.nio.ByteBuffer;

import rlib.network.packet.SendablePacket;
import rlib.util.Util;

/**
 * Базовая реализация отправляемого пакета.
 * 
 * @author Ronn
 */
public abstract class AbstractSendablePacket<C> extends AbstractPacket<C> implements SendablePacket<C> {

	@Override
	public String toString() {
		return "AbstractSendablePacket [owner=" + owner + ", name=" + name + "]";
	}

	@Override
	public void write(final ByteBuffer buffer) {
		try {
			writeImpl(buffer);
		} catch(final Exception e) {
			LOGGER.warning(this, e);
			LOGGER.warning(this, "Buffer " + buffer + "\n" + Util.hexdump(buffer.array(), buffer.position()));
		}
	}

	/**
	 * Процесс записи пакета в указанный буффер.
	 */
	protected void writeImpl(final ByteBuffer buffer) {
		LOGGER.warning(this, new Exception("unsupperted method"));
	}
}