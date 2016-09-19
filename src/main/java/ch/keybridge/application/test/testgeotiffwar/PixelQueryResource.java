/*
 * Copyright 2016 Key Bridge LLC.
 *
 * All rights reserved. Use is subject to license terms.
 * This software is protected by copyright.
 *
 * See the License for specific language governing permissions and
 * limitations under the License.
 */
package ch.keybridge.application.test.testgeotiffwar;

import java.awt.geom.Point2D;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.geotools.coverage.grid.GridCoverage2D;
import org.geotools.data.DataSourceException;
import org.geotools.gce.geotiff.GeoTiffReader;

/**
 *
 * @author Key Bridge LLC
 */
@Path("pixel")
@Produces({MediaType.TEXT_PLAIN})
public class PixelQueryResource {

  private static final Logger LOG = Logger.getLogger(PixelQueryResource.class.getName());

  public PixelQueryResource() {
    LOG.info("Initialize new PixelQueryResource");
  }

  @GET
  @Path("query/{latitude}/{longitude}")
  public Response pixelQuery(@PathParam("latitude") Double latitude, @PathParam("longitude") Double longitude) {
    try {
      LOG.log(Level.INFO, "Pixel Query at {0}, {1}", new Object[]{latitude, longitude});
      /**
       * For this test use a static GeoTIFF elevation file. This may be
       * downloaded from
       * http://ftp.geogratis.gc.ca/pub/nrcan_rncan/elevation/cdem_mnec/023/cdem_dem_023L_tif.zip
       */
      java.nio.file.Path geoTIFFFile = java.nio.file.Paths.get("/tmp/cdem_dem_023L.tif");
      LOG.log(Level.INFO, "Evaluating on data file {0}", geoTIFFFile);
      /**
       * Read in the GeoTIFF file.
       */
      LOG.info("Initializing GeoTIFF Reader.");
      GeoTiffReader reader = new GeoTiffReader(geoTIFFFile.toFile());
      LOG.info("Initialized GeoTIFF Reader OK.");

      LOG.info("Reading GeoTIFF GridCoverage");
      GridCoverage2D coverage = reader.read(null);
      LOG.info("Read GeoTIFF GridCoverage OK");

      LOG.info("Evaluating the position");
      double value = coverage.evaluate(new Point2D.Double(longitude, latitude), (double[]) null)[0];
      LOG.log(Level.INFO, "Evaluated the position OK. The value is {0}", value);

      return Response.ok().build();

    } catch (DataSourceException ex) {
      Logger.getLogger(PixelQueryResource.class.getName()).log(Level.SEVERE, null, ex);
    } catch (IOException ex) {
      Logger.getLogger(PixelQueryResource.class.getName()).log(Level.SEVERE, null, ex);
    }
    /**
     * Failed to return a value.
     */
    LOG.severe("Failed to return a value.");
    return null;

  }

}
