/*
 * Created on Oct 2, 2011
 * Copyright 2010 by Eduard Weissmann (edi.weissmann@gmail.com).
 * 
 * This file is part of the Sejda source code
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.sejda.cli.transformer;

import org.sejda.cli.model.PdfToMultipleTiffTaskCliArguments;
import org.sejda.model.parameter.image.PdfToMultipleTiffParameters;

/**
 * {@link CommandCliArgumentsTransformer} for the PdfToMultipleTiff task command line interface
 * 
 * @author Eduard Weissmann
 * 
 */
public class PdfToMultipleTiffCliArgumentsTransformer extends BaseCliArgumentsTransformer implements
        CommandCliArgumentsTransformer<PdfToMultipleTiffTaskCliArguments, PdfToMultipleTiffParameters> {

    /**
     * Transforms {@link PdfToMultipleTiffTaskCliArguments} to {@link PdfToMultipleTiffParameters}
     * 
     * @param taskCliArguments
     * @return populated task parameters
     */
    @Override
    public PdfToMultipleTiffParameters toTaskParameters(PdfToMultipleTiffTaskCliArguments taskCliArguments) {
        PdfToMultipleTiffParameters parameters = new PdfToMultipleTiffParameters(taskCliArguments.getColorType()
                .getEnumValue());

        parameters.setCompressionType(taskCliArguments.getCompressionType().getEnumValue());

        populateSourceParameters(parameters, taskCliArguments);
        populateAbstractMultipleImageParameters(parameters, taskCliArguments);
        populateOutputPrefix(parameters, taskCliArguments);

        if (taskCliArguments.isPageSelection()) {
            parameters.addAllPageRanges(taskCliArguments.getPageSelection().getPageRangeSet());
        }

        return parameters;
    }
}
