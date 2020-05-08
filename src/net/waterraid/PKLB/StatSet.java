package net.waterraid.PKLB;

import java.util.UUID;

public class StatSet {
    private final UUID _uuid;
    private final Long _stat;
    public StatSet(UUID uuid, Long stat) {
        _uuid = uuid;
        _stat = stat;
    }
    public UUID getID() {
        return _uuid;
    }
    public long getStat(){
        return _stat;
    }
}
