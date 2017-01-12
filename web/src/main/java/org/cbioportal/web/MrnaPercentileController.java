package org.cbioportal.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.cbioportal.model.MrnaPercentile;
import org.cbioportal.service.MrnaPercentileService;
import org.cbioportal.service.exception.GeneticProfileNotFoundException;
import org.cbioportal.service.exception.SampleNotFoundException;
import org.cbioportal.web.config.annotation.InternalApi;
import org.cbioportal.web.parameter.PagingConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Size;
import java.util.List;

@InternalApi
@RestController
@Validated
@Api(tags = "mRNA Percentile", description = " ")
public class MrnaPercentileController {

    @Autowired
    private MrnaPercentileService mrnaPercentileService;

    @RequestMapping(value = "/genetic-profiles/{geneticProfileId}/mrna-percentile/fetch", method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("Get mutations in a genetic profile")
    public ResponseEntity<List<MrnaPercentile>> getMrnaPercentile(
        @ApiParam(required = true, value = "Genetic Profile ID e.g. acc_tcga_mutations")
        @PathVariable String geneticProfileId,
        @ApiParam(required = true, value = "Sample ID e.g. TCGA-OR-A5J2-01")
        @RequestParam String sampleId,
        @ApiParam(required = true, value = "List of Entrez Gene IDs")
        @Size(min = 1, max = PagingConstants.MAX_PAGE_SIZE)
        @RequestBody List<Integer> entrezGeneIds)
        throws SampleNotFoundException, GeneticProfileNotFoundException {
        
            return new ResponseEntity<>(
                mrnaPercentileService.fetchMrnaPercentile(geneticProfileId, sampleId, entrezGeneIds), HttpStatus.OK);
    }
}
