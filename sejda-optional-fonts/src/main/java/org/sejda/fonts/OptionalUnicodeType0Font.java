/*
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
package org.sejda.fonts;

import java.io.InputStream;

import org.sejda.model.pdf.FontResource;

public enum OptionalUnicodeType0Font implements FontResource {
    NOTO_SANS_CJK_REGULAR("/optional/fonts/sans/NotoSansCJKtc-Regular.ttf"),
    NOTO_SANS_ARMENIAN_REGULAR("/optional/fonts/sans/NotoSansArmenian-Regular.ttf");

    private String resource;

    OptionalUnicodeType0Font(String resource) {
        this.resource = resource;
    }

    @Override
    public String getResource() {
        return resource;
    }

    @Override
    public InputStream getFontStream() {
        return this.getClass().getResourceAsStream(resource);
    }
}
