// THIS SOURCE CODE IS SUPPLIED "AS IS" WITHOUT WARRANTY OF ANY KIND, AND ITS AUTHOR AND THE JOURNAL OF MACHINE LEARNING RESEARCH (JMLR) AND JMLR'S PUBLISHERS AND DISTRIBUTORS, DISCLAIM ANY AND ALL WARRANTIES, INCLUDING BUT NOT LIMITED TO ANY IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE, AND ANY WARRANTIES OR NON INFRINGEMENT. THE USER ASSUMES ALL LIABILITY AND RESPONSIBILITY FOR USE OF THIS SOURCE CODE, AND NEITHER THE AUTHOR NOR JMLR, NOR JMLR'S PUBLISHERS AND DISTRIBUTORS, WILL BE LIABLE FOR DAMAGES OF ANY KIND RESULTING FROM ITS USE. Without lim- iting the generality of the foregoing, neither the author, nor JMLR, nor JMLR's publishers and distributors, warrant that the Source Code will be error-free, will operate without interruption, or will meet the needs of the user.
// 
// --------------------------------------------------------------------------
// 
// Copyright 2011 Stephen Piccolo
// 
// This file is part of ML-Flex.
// 
// ML-Flex is free software: you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// any later version.
// 
// ML-Flex is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
// 
// You should have received a copy of the GNU General Public License
// along with ML-Flex. If not, see <http://www.gnu.org/licenses/>.

package mlflex.dataprocessors.tcga;

import mlflex.core.DataValueMeta;
import mlflex.core.DataValues;
import mlflex.helper.ListUtilities;
import mlflex.helper.MiscUtilities;

import java.util.ArrayList;

/** This data processor is designed to parse information from the TCGA clinical data; specifically, it handles histology data. This data can be downloaded from http://tcga-data.nci.nih.gov/tcga/dataAccessMatrix.htm. This code has so far been tested only on the glioblastoma multiforme data.
 * @author Stephen Piccolo
 */
public class TcgaHistologyDataProcessor extends AbstractTcgaDataProcessor
{
    private TcgaClinicalTabDataParser _parser;

    /** Constructor */
    public TcgaHistologyDataProcessor() throws Exception
    {
        _parser = new TcgaClinicalTabDataParser(this, GetInputDataDir());
    }

    @Override
    protected void ParseInputData() throws Exception
    {
        _parser.SaveRawData();
    }

    @Override
    public DataValueMeta GetDataPointMeta(String dataPointName)
    {
        return _parser.FindDataValueMeta(dataPointName);
    }

    @Override
    protected boolean KeepRawInstance(DataValues instance) throws Exception
    {
        return _parser.KeepPatient(instance);
    }

    @Override
    protected DataValues TransformRawInstance(DataValues instance) throws Exception
    {
        DataValues transformed = instance.CopyStructure();

        transformed.AddDataPoint("number proliferating cells", instance.GetDataPointValue("NUMBERPROLIFERATINGCELLS"));
        transformed.AddDataPoint("percent tumor cells", instance.GetDataPointValue("PERCENTTUMORCELLS"));
        transformed.AddDataPoint("percent tumor nuclei", instance.GetDataPointValue("PERCENTTUMORNUCLEI"));
        transformed.AddDataPoint("percent necrosis", instance.GetDataPointValue("PERCENTNECROSIS"));
        transformed.AddDataPoint("percent stromal cells", instance.GetDataPointValue("PERCENTSTROMALCELLS"));
        transformed.AddDataPoint("percent inflammatory infiltration", instance.GetDataPointValue("PERCENTINFLAMINFILTRATION"));
        transformed.AddDataPoint("percent lymphocyte infiltration", instance.GetDataPointValue("PERCENTLYMPHOCYTEINFILTRATION"));
        transformed.AddDataPoint("percent monocyte infiltration", instance.GetDataPointValue("PERCENTMONOCYTEINFILTRATION"));
        transformed.AddDataPoint("percent granulocyte infiltration", instance.GetDataPointValue("PERCENTGRANULOCYTEINFILTRATION"));
        transformed.AddDataPoint("percent neutrophil infiltration", instance.GetDataPointValue("PERCENTNEUTROPHILINFILTRATION"));
        transformed.AddDataPoint("percent osinophil infiltration", instance.GetDataPointValue("PERCENTEOSINOPHILINFILTRATION"));
        transformed.AddDataPoint("endothelial proliferation", instance.GetDataPointValue("ENDOTHELIALPROLIFERATION"));
        transformed.AddDataPoint("nuclear pleomorphism", instance.GetDataPointValue("NUCLEARPLEOMORPHISM"));
        transformed.AddDataPoint("palisading necrosis", instance.GetDataPointValue("PALISADINGNECROSIS"));
        transformed.AddDataPoint("cellularity", instance.GetDataPointValue("CELLULARITY"));

        return transformed;
    }

    @Override
    public ArrayList<String> GetPriorKnowledgeSelectedFeatures()
    {
        return MiscUtilities.FormatNames(ListUtilities.CreateStringList("percent necrosis"));
        // Necrosis: Burger1987 (but not independent of age), Lacroix2001, Homma2006
        // Pleomorphism: Burger1987
    }
}