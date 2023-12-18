package fmt.cerulean.util;

import net.minecraft.util.math.MathHelper;

public record Vec2d(double x, double z) {
    public static Vec2d of(double x, double z) {
        return new Vec2d(x, z);
    }

    public double distSqr(Vec2d other) {
        double dx = this.x - other.x;
        double dy = this.z - other.z;
        return dx * dx + dy * dy;
    }

    public Vec2i floor() {
        return new Vec2i(MathHelper.floor(x), MathHelper.floor(z));
    }
}
