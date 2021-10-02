package pl.spigotplugin.objects.guild;


import org.bukkit.entity.Player;

public class Fight {
    private Player player;
    private Player attacked;
    private long fightTime;

    public Player getPlayer() {
        return this.player;
    }

    public Player getAttacked() {
        return this.attacked;
    }

    public long getFightTime() {
        return this.fightTime;
    }

    public void setPlayer(final Player player) {
        this.player = player;
    }

    public void setAttacked(final Player attacked) {
        this.attacked = attacked;
    }

    public void setFightTime(final long fightTime) {
        this.fightTime = fightTime;
    }

    @Override
    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Fight)) {
            return false;
        }
        final Fight other = (Fight)o;
        if (!other.canEqual(this)) {
            return false;
        }
        if (this.getFightTime() != other.getFightTime()) {
            return false;
        }
        final Object this$player = this.getPlayer();
        final Object other$player = other.getPlayer();
        Label_0079: {
            if (this$player == null) {
                if (other$player == null) {
                    break Label_0079;
                }
            }
            else if (this$player.equals(other$player)) {
                break Label_0079;
            }
            return false;
        }
        final Object this$attacked = this.getAttacked();
        final Object other$attacked = other.getAttacked();
        if (this$attacked == null) {
            if (other$attacked == null) {
                return true;
            }
        }
        else if (this$attacked.equals(other$attacked)) {
            return true;
        }
        return false;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof Fight;
    }

    @Override
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final long $fightTime = this.getFightTime();
        result = result * 59 + (int)($fightTime >>> 32 ^ $fightTime);
        final Object $player = this.getPlayer();
        result = result * 59 + (($player == null) ? 43 : $player.hashCode());
        final Object $attacked = this.getAttacked();
        result = result * 59 + (($attacked == null) ? 43 : $attacked.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "Fight(player=" + this.getPlayer() + ", attacked=" + this.getAttacked() + ", fightTime=" + this.getFightTime() + ")";
    }

    public Fight(final Player player, final Player attacked, final long fightTime) {
        this.player = player;
        this.attacked = attacked;
        this.fightTime = fightTime;
    }
}