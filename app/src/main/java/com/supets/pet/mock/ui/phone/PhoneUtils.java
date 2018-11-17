package com.supets.pet.mock.ui.phone;

import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;

import java.util.ArrayList;
import java.util.List;

public class PhoneUtils {


    public static List<ContactsBean> getContactInfo(Context context) {
        ArrayList<ContactsBean> list = new ArrayList<>();
        //联系人的Uri，也就是content://com.android.contacts/contacts
        Uri uri = ContactsContract.Contacts.CONTENT_URI;
        //指定获取_id和display_name两列数据，display_name即为姓名
        String[] projection = new String[]{
                ContactsContract.Contacts._ID,
                ContactsContract.Contacts.DISPLAY_NAME
        };
        //根据Uri查询相应的ContentProvider，cursor为获取到的数据集
        Cursor cursor = context.getContentResolver().query(uri, projection, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                Long id = cursor.getLong(0);
                //获取姓名
                String name = cursor.getString(1);
                //指定获取NUMBER这一列数据
                String[] phoneProjection = new String[]{
                        ContactsContract.CommonDataKinds.Phone.NUMBER
                };

                //根据联系人的ID获取此人的电话号码
                Cursor phonesCusor = context.getContentResolver().query(
                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        phoneProjection,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=" + id,
                        null,
                        null);

                //因为每个联系人可能有多个电话号码，所以需要遍历
                if (phonesCusor != null && phonesCusor.moveToFirst()) {
                    do {
                        String number = phonesCusor.getString(0);

                        if (isUserNumber(formatMobileNumber(number))) {// 是否是手机号码
                            ContactsBean simCardTemp = new ContactsBean();
                            simCardTemp.setPhone(formatMobileNumber(number));
                            simCardTemp.setName(name);
                            if (!list.contains(simCardTemp)) {
                                list.add(simCardTemp);
                            }
                        }

                    } while (phonesCusor.moveToNext());
                }

                if (phonesCusor != null) {
                    phonesCusor.close();
                }

            } while (cursor.moveToNext());
        }

        // 获取sim卡的联系人--1
        try {
            getSimContact("content://icc/adn", list, context);

            getSimContact("content://icc/adn/subId/#", list, context);

            getSimContact("content://icc/sdn", list, context);

            getSimContact("content://icc/sdn/subId/#", list, context);

            getSimContact("content://icc/fdn", list, context);

            getSimContact("content://icc/fdn/subId/#", list, context);

        } catch (Exception e) {
            e.printStackTrace();
        }

        if (cursor != null) {
            cursor.close();
        }
        return list;
    }

    private static void getSimContact(String adn, List<ContactsBean> list, Context context) {
        // 读取SIM卡手机号,有三种可能:content://icc/adn || content://icc/sdn || content://icc/fdn
        // 具体查看类 IccProvider.java
        Cursor cursor = null;
        try {
            Uri uri = Uri.parse(adn);
            cursor = context.getContentResolver().query(uri, null,
                    null, null, null);
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    // 取得联系人名字
                    int nameIndex = cursor.getColumnIndex("name");
                    // 取得电话号码
                    int numberIndex = cursor.getColumnIndex("number");
                    String number = cursor.getString(numberIndex);// 手机号
                    if (isUserNumber(formatMobileNumber(number))) {// 是否是手机号码
                        ContactsBean simCardTemp = new ContactsBean();
                        simCardTemp.setPhone(formatMobileNumber(number));
                        simCardTemp.setName(cursor.getString(nameIndex));
                        if (!list.contains(simCardTemp)) {
                            list.add(simCardTemp);
                        }
                    }
                }
                cursor.close();
            }
        } catch (Exception e) {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    private static String formatMobileNumber(String num2) {
        String num;
        if (num2 != null) {
            // 有的通讯录格式为135-1568-1234
            num = num2.replaceAll("-", "");
            // 有的通讯录格式中- 变成了空格
            num = num.replaceAll(" ", "");
            if (num.startsWith("+86")) {
                num = num.substring(3);
            } else if (num.startsWith("86")) {
                num = num.substring(2);
            } else if (num.startsWith("86")) {
                num = num.substring(2);
            }
        } else {
            num = "";
        } // 有些手机号码格式 86后有空格
        return num.trim();
    }

    private static boolean isUserNumber(String num) {
        if (null == num || "".equalsIgnoreCase(num)) {
            return false;
        }
        boolean re = false;
        if (num.length() == 11) {
            if (num.startsWith("1")) {
                re = true;
            }
        }
        return re;
    }

    public static void insertData(Context context, String name, String phone) {
        try {
            Uri uri = Uri.parse("content://com.android.contacts/raw_contacts");
            ContentResolver resolver = context.getContentResolver();
            ArrayList<ContentProviderOperation> operations = new ArrayList<>();
            //此处.withValue("account_name", null)一定要加，不然会抛NullPointerException
            ContentProviderOperation operation1 = ContentProviderOperation
                    .newInsert(uri).withValue("account_name", null).build();
            operations.add(operation1);
            // 向data添加数据
            uri = Uri.parse("content://com.android.contacts/data");
            //添加姓名
            ContentProviderOperation operation2 = ContentProviderOperation
                    .newInsert(uri).withValueBackReference("raw_contact_id", 0)
                    .withValue("mimetype", "vnd.android.cursor.item/name")
                    .withValue("data2", name).build();
            operations.add(operation2);
            //添加手机数据
            ContentProviderOperation operation3 = ContentProviderOperation
                    .newInsert(uri).withValueBackReference("raw_contact_id", 0)
                    .withValue("mimetype", "vnd.android.cursor.item/phone_v2")
                    .withValue("data2", "2")
                    .withValue("data1", phone).build();
            operations.add(operation3);
            resolver.applyBatch("com.android.contacts", operations);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
