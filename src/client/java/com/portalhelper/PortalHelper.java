package com.portalhelper;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommands;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.network.chat.Component;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.context.CommandContext;

public class PortalHelper implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {
            dispatcher.register(
                    ClientCommands.literal("portal")
                            .then(ClientCommands.literal("overworld")
                                    .then(ClientCommands.argument("x", DoubleArgumentType.doubleArg())
                                            .then(ClientCommands.argument("y", DoubleArgumentType.doubleArg())
                                                    .then(ClientCommands.argument("z", DoubleArgumentType.doubleArg())
                                                            .executes(ctx -> overworldToNether(ctx))
                                                    )
                                            )
                                    )
                            )
                            .then(ClientCommands.literal("nether")
                                    .then(ClientCommands.argument("x", DoubleArgumentType.doubleArg())
                                            .then(ClientCommands.argument("y", DoubleArgumentType.doubleArg())
                                                    .then(ClientCommands.argument("z", DoubleArgumentType.doubleArg())
                                                            .executes(ctx -> netherToOverworld(ctx))
                                                    )
                                            )
                                    )
                            )
            );
        });
    }

    // Estás no Overworld -> calcula coords no Nether (divide por 8)
    private int overworldToNether(CommandContext<FabricClientCommandSource> ctx) {
        double x = DoubleArgumentType.getDouble(ctx, "x");
        double y = DoubleArgumentType.getDouble(ctx, "y");
        double z = DoubleArgumentType.getDouble(ctx, "z");

        double nx = x / 8.0;
        double nz = z / 8.0;
        double ny = Math.min(y, 127.0);

        ctx.getSource().sendFeedback(Component.literal(
                "§6[PortalHelper] §aOverworld §7(" + fmt(x) + ", " + fmt(y) + ", " + fmt(z) + ")" +
                        " §f➜ §cNether §7(" + fmt(nx) + ", " + fmt(ny) + ", " + fmt(nz) + ")"
        ));
        return 1;
    }

    // Estás no Nether -> calcula coords no Overworld (multiplica por 8)
    private int netherToOverworld(CommandContext<FabricClientCommandSource> ctx) {
        double x = DoubleArgumentType.getDouble(ctx, "x");
        double y = DoubleArgumentType.getDouble(ctx, "y");
        double z = DoubleArgumentType.getDouble(ctx, "z");

        double ox = x * 8.0;
        double oz = z * 8.0;

        ctx.getSource().sendFeedback(Component.literal(
                "§6[PortalHelper] §cNether §7(" + fmt(x) + ", " + fmt(y) + ", " + fmt(z) + ")" +
                        " §f➜ §aOverworld §7(" + fmt(ox) + ", " + fmt(y) + ", " + fmt(oz) + ")"
        ));
        return 1;
    }

    private String fmt(double value) {
        if (value == Math.floor(value) && !Double.isInfinite(value)) {
            return String.valueOf((int) value);
        }
        return String.format("%.1f", value);
    }
}