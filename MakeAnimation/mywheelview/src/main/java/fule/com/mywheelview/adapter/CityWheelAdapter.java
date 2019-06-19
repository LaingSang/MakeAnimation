/*
 *  Copyright 2010 Yuri Kanivets
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package fule.com.mywheelview.adapter;

import android.content.Context;

import java.util.List;

import fule.com.mywheelview.bean.AddressDetailsEntity;

/**
 * 城市适配器
 */
public class CityWheelAdapter extends BaseWheelAdapter<AddressDetailsEntity.ProvinceEntity.CityEntity> {
    public CityWheelAdapter(Context context, List<AddressDetailsEntity.ProvinceEntity.CityEntity> list) {
        super(context, list);
    }

    @Override
    protected CharSequence getItemText(int index) {
        AddressDetailsEntity.ProvinceEntity.CityEntity data = getItemData(index);
        if (data != null) {
            return data.Name;
        }
        return null;
    }
}
