package cyano.electricadvantage.graphics;

import cyano.electricadvantage.ElectricAdvantage;
import cyano.electricadvantage.machines.ElectricDrillTileEntity;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT) // This is needed for classes that extend client-only classes
public class LaserDrillRenderer extends TileEntitySpecialRenderer{

	
	private final ResourceLocation texture = new ResourceLocation(ElectricAdvantage.MODID+":textures/materials/laser_beam.png");

	public LaserDrillRenderer() {
		super();
	}

	
	@Override
	public void renderTileEntityAt(final TileEntity te, final double x, final double y, final double z, final float partialTick, int meta) {
		if(te instanceof ElectricDrillTileEntity){
			// partialTick is guaranteed to range from 0 to 1
			GlStateManager.pushMatrix();
			GlStateManager.translate((float)x, (float)y, (float)z);

			render((ElectricDrillTileEntity)te,te.getWorld(),te.getPos(),partialTick);
			
			GlStateManager.popMatrix();
		}
	}
	
	private void render(ElectricDrillTileEntity e, World world, BlockPos pos, float partialTick){
		float laserBlastLength = e.getDrillLength();

		if(laserBlastLength > 0){
			this.bindTexture(texture);
			final Tessellator tessellator = Tessellator.getInstance();

			final VertexBuffer worldRenderer = tessellator.getBuffer();


			final float laserU0 = 0;
			final float laserU1 = 0;
			final float laserV0 = 1.0f;
			final float laserV1 = 1.0f;

			final float laserRadius = 0.125f;


			EnumFacing dir = e.getFacing();

			float rotY = 0, rotX = 0;
			switch(dir){
			case NORTH:{
				// do nothing
				break;
			}
			case EAST:{
				rotY = -90;
				break;
			}
			case SOUTH:{
				rotY = 180;
				break;
			}
			case WEST:{
				rotY = 90;
				break;
			}
			case UP:{
				rotX = 90;
				break;
			}
			case DOWN:{
				rotX = -90;
				break;
			}
			default:{
				// do nothing
				break;
			}
			}
			
			GlStateManager.disableLighting();
			
			GlStateManager.translate(0.5f, 0.5f, 0.5f);
			GlStateManager.rotate(rotY, 0.0f, 1.0f, 0.0f);
			GlStateManager.rotate(rotX, 1.0f, 0.0f, 0.0f);

			int lightValue = 15 << 20 | 15 << 4;
			int lmapX = lightValue >> 16 & 0xFFFF;
			int lmapY = lightValue & 0xFFFF;
			worldRenderer.begin(7, DefaultVertexFormats.POSITION_TEX_LMAP_COLOR);

			final double x1, y1, z1, x2, y2, z2,  x3, y3, z3, x4, y4, z4;
			x1 = 0;
			y1 = -laserRadius;
			z1 = 0;
			x2 = 0;
			y2 = laserRadius;
			z2 = -laserBlastLength;

			x3 = -laserRadius;
			y3 = 0;
			z3 = z1;
			x4 = laserRadius;
			y4 = 0;
			z4 = z2;

			worldRenderer.pos( x1, y1, z1).tex(laserU0, laserV1).lightmap(lmapX, lmapY).color(1f, 1f, 1f, 1f).endVertex();
			worldRenderer.pos( x2, y1, z2).tex(laserU1, laserV1).lightmap(lmapX, lmapY).color(1f, 1f, 1f, 1f).endVertex();
			worldRenderer.pos( x2, y2, z2).tex(laserU1, laserV0).lightmap(lmapX, lmapY).color(1f, 1f, 1f, 1f).endVertex();
			worldRenderer.pos( x1, y2, z1).tex(laserU0, laserV0).lightmap(lmapX, lmapY).color(1f, 1f, 1f, 1f).endVertex();

			worldRenderer.pos( x1, y2, z1).tex(laserU0, laserV1).lightmap(lmapX, lmapY).color(1f, 1f, 1f, 1f).endVertex();
			worldRenderer.pos( x2, y2, z2).tex(laserU1, laserV1).lightmap(lmapX, lmapY).color(1f, 1f, 1f, 1f).endVertex();
			worldRenderer.pos( x2, y1, z2).tex(laserU1, laserV0).lightmap(lmapX, lmapY).color(1f, 1f, 1f, 1f).endVertex();
			worldRenderer.pos( x1, y1, z1).tex(laserU0, laserV0).lightmap(lmapX, lmapY).color(1f, 1f, 1f, 1f).endVertex();


			worldRenderer.pos( x3, y3, z3).tex(laserU0, laserV1).lightmap(lmapX, lmapY).color(1f, 1f, 1f, 1f).endVertex();
			worldRenderer.pos( x3, y4, z4).tex(laserU1, laserV1).lightmap(lmapX, lmapY).color(1f, 1f, 1f, 1f).endVertex();
			worldRenderer.pos( x4, y4, z4).tex(laserU1, laserV0).lightmap(lmapX, lmapY).color(1f, 1f, 1f, 1f).endVertex();
			worldRenderer.pos( x4, y3, z3).tex(laserU0, laserV0).lightmap(lmapX, lmapY).color(1f, 1f, 1f, 1f).endVertex();

			worldRenderer.pos( x4, y3, z3).tex(laserU0, laserV1).lightmap(lmapX, lmapY).color(1f, 1f, 1f, 1f).endVertex();
			worldRenderer.pos( x4, y4, z4).tex(laserU1, laserV1).lightmap(lmapX, lmapY).color(1f, 1f, 1f, 1f).endVertex();
			worldRenderer.pos( x3, y4, z4).tex(laserU1, laserV0).lightmap(lmapX, lmapY).color(1f, 1f, 1f, 1f).endVertex();
			worldRenderer.pos( x3, y3, z3).tex(laserU0, laserV0).lightmap(lmapX, lmapY).color(1f, 1f, 1f, 1f).endVertex();

			tessellator.draw();
			GlStateManager.enableLighting();

		}
	}

	
}
