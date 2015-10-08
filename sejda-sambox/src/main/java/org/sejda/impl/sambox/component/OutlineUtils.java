/* 
 * This file is part of the Sejda source code
 * Copyright 2015 by Andrea Vacondio (andrea.vacondio@gmail.com).
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
package org.sejda.impl.sambox.component;

import java.io.IOException;
import java.util.Optional;

import org.sejda.sambox.pdmodel.PDDocument;
import org.sejda.sambox.pdmodel.PDDocumentCatalog;
import org.sejda.sambox.pdmodel.interactive.action.PDAction;
import org.sejda.sambox.pdmodel.interactive.action.PDActionGoTo;
import org.sejda.sambox.pdmodel.interactive.documentnavigation.destination.PDDestination;
import org.sejda.sambox.pdmodel.interactive.documentnavigation.destination.PDNamedDestination;
import org.sejda.sambox.pdmodel.interactive.documentnavigation.destination.PDPageDestination;
import org.sejda.sambox.pdmodel.interactive.documentnavigation.outline.PDOutlineItem;
import org.sejda.sambox.pdmodel.interactive.documentnavigation.outline.PDOutlineNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Utility methods related to outline handling in SAMBox
 * 
 * @author Andrea Vacondio
 *
 */
public final class OutlineUtils {

    private static final Logger LOG = LoggerFactory.getLogger(OutlineUtils.class);

    private OutlineUtils() {
        // utility
    }

    /**
     * @param document
     * @return the max outline level where a page destination (page destination, named destination, goto action) is defined.
     */
    public static int getMaxOutlineLevel(PDDocument document) {
        return getMaxOutlineLevel(document.getDocumentCatalog().getDocumentOutline(), document.getDocumentCatalog(), 0);
    }

    private static int getMaxOutlineLevel(PDOutlineNode node, PDDocumentCatalog catalog, int parentLevel) {
        int maxLevel = parentLevel;
        if (node != null) {
            for (PDOutlineItem current : node.children()) {
                if (isPageDestination(current, catalog)) {
                    int maxBookmarkBranchLevel = getMaxOutlineLevel(current, catalog, parentLevel + 1);
                    if (maxBookmarkBranchLevel > maxLevel) {
                        maxLevel = maxBookmarkBranchLevel;
                    }
                }
            }
        }
        return maxLevel;
    }

    /**
     * @param current
     *            the outline item
     * @param destinations
     *            the named destinations tree to look for in case of {@link PDNamedDestination}
     * @return the {@link PDPageDestination} for the given {@link PDOutlineItem} or an empty {@link Optional} if the destination is not a page. In case the outline item has a named
     *         destination, it is resolved against the given names tree.
     */
    public static Optional<PDPageDestination> toPageDestination(PDOutlineItem current, PDDocumentCatalog catalog) {
        try {
            PDDestination dest = current.getDestination();
            if (dest == null) {
                PDAction outlineAction = current.getAction();
                if (outlineAction instanceof PDActionGoTo) {
                    dest = ((PDActionGoTo) outlineAction).getDestination();
                }
            }
            if (dest instanceof PDNamedDestination && catalog != null) {
                dest = catalog.findNamedDestinationPage((PDNamedDestination) dest);
            }
            if (dest instanceof PDPageDestination) {
                return Optional.of((PDPageDestination) dest);
            }
        } catch (IOException e) {
            LOG.warn("Unable to get outline item destination ", e);
        }
        return Optional.empty();
    }

    private static boolean isPageDestination(PDOutlineItem current, PDDocumentCatalog catalog) {
        return toPageDestination(current, catalog).isPresent();
    }

    /**
     * Copies the dictionary from the given {@link PDOutlineItem} to the destination one
     * 
     * @param from
     * @param to
     */
    public static void copyOutlineDictionary(PDOutlineItem from, PDOutlineItem to) {
        to.setTitle(from.getTitle());
        to.setTextColor(from.getTextColor());
        to.setBold(from.isBold());
        to.setItalic(from.isItalic());
        if (from.isNodeOpen()) {
            to.openNode();
        } else {
            to.closeNode();
        }
    }
}