/*
 * Created on 07 mar 2016
 * Copyright 2015 by Andrea Vacondio (andrea.vacondio@gmail.com).
 * This file is part of Sejda.
 *
 * Sejda is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Sejda is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with Sejda.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.sejda.impl.sambox.component;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.isEmptyOrNullString;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.sejda.model.exception.TaskException;
import org.sejda.sambox.pdmodel.PDDocument;
import org.sejda.sambox.pdmodel.PDPage;

import java.io.IOException;

/**
 * @author Andrea Vacondio
 *
 */
public class FilenameFooterWriterTest {

    @Test
    public void write() throws TaskException {
        PDDocument doc = new PDDocument();
        PDPage page = new PDPage();
        doc.addPage(page);
        new FilenameFooterWriter(true, doc).addFooter(page, "My Footer", 20);
        assertThat(new PdfTextExtractorByArea().extractFooterText(page).trim(), is("My Footer 20"));
    }

    @Test
    public void write_long_filename_that_needs_truncation() throws TaskException, IOException {
        PDDocument doc = new PDDocument();
        PDPage page = new PDPage();
        doc.addPage(page);
        new FilenameFooterWriter(true, doc).addFooter(page, "My very long title that will not fit on the page and needs to be truncated so that it will not overflow and cover the page number and generally look not so nice", 20);
        assertThat(new PdfTextExtractorByArea().extractFooterText(page).trim(), is("My very long title that will not fit on the page and needs to be truncated so that it will not overflow and cover the page num 20"));
    }

    @Test
    public void dontWrite() throws TaskException {
        PDDocument doc = new PDDocument();
        PDPage page = new PDPage();
        doc.addPage(page);
        new FilenameFooterWriter(false, doc).addFooter(page, "My Footer", 20);
        assertThat(new PdfTextExtractorByArea().extractFooterText(page).trim(), isEmptyOrNullString());
    }
}
