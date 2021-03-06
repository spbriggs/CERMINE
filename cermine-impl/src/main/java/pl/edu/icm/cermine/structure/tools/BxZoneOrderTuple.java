/**
 * This file is part of CERMINE project.
 * Copyright (c) 2011-2018 ICM-UW
 *
 * CERMINE is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * CERMINE is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with CERMINE. If not, see <http://www.gnu.org/licenses/>.
 */
package pl.edu.icm.cermine.structure.tools;

import pl.edu.icm.cermine.structure.model.BxZone;

/**
 * @author Pawel Szostek
 */
public class BxZoneOrderTuple {

    private BxZone zone;
    private int order;

    public BxZoneOrderTuple(BxZone zone, int order) {
        this.zone = zone;
        this.order = order;
    }

    public BxZone getZone() {
        return zone;
    }

    public void setZone(BxZone zone) {
        this.zone = zone;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

}
