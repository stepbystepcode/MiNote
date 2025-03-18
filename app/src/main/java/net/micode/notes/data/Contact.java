/*
 * Copyright (c) 2010-2011, The MiCode Open Source Community (www.micode.net)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.micode.notes.data;

/**
 * Contact 类是联系人数据处理的实现类
 * 
 * 功能：
 * - 提供联系人数据的访问和查询
 * - 处理联系人与笔记的关联
 * - 支持联系人数据的格式化和显示
 * - 管理联系人数据的缓存和更新
 * - 提供联系人选择和过滤功能
 * 
 * 改进点：
 * 1. 应采用更现代的联系人API
 * 2. 缺少联系人数据的同步机制
 * 3. 应提供更丰富的联系人信息展示
 * 4. 缺少联系人权限管理和隐私保护
 * 
 * 不足之处：
 * 1. 联系人数据获取方式较为陈旧
 * 2. 缺少适当的错误处理和异常恢复
 * 3. 未提供完整的联系人搜索功能
 * 4. 与系统联系人应用集成不够紧密
 */

import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.Data;
import android.telephony.PhoneNumberUtils;
import android.util.Log;

import java.util.HashMap;

public class Contact {
    private static HashMap<String, String> sContactCache;
    private static final String TAG = "Contact";

    private static final String CALLER_ID_SELECTION = "PHONE_NUMBERS_EQUAL(" + Phone.NUMBER
    + ",?) AND " + Data.MIMETYPE + "='" + Phone.CONTENT_ITEM_TYPE + "'"
    + " AND " + Data.RAW_CONTACT_ID + " IN "
            + "(SELECT raw_contact_id "
            + " FROM phone_lookup"
            + " WHERE min_match = '+')";

    public static String getContact(Context context, String phoneNumber) {
        if(sContactCache == null) {
            sContactCache = new HashMap<String, String>();
        }

        if(sContactCache.containsKey(phoneNumber)) {
            return sContactCache.get(phoneNumber);
        }

        String selection = CALLER_ID_SELECTION.replace("+",
                PhoneNumberUtils.toCallerIDMinMatch(phoneNumber));
        Cursor cursor = context.getContentResolver().query(
                Data.CONTENT_URI,
                new String [] { Phone.DISPLAY_NAME },
                selection,
                new String[] { phoneNumber },
                null);

        if (cursor != null && cursor.moveToFirst()) {
            try {
                String name = cursor.getString(0);
                sContactCache.put(phoneNumber, name);
                return name;
            } catch (IndexOutOfBoundsException e) {
                Log.e(TAG, " Cursor get string error " + e.toString());
                return null;
            } finally {
                cursor.close();
            }
        } else {
            Log.d(TAG, "No contact matched with number:" + phoneNumber);
            return null;
        }
    }
}
