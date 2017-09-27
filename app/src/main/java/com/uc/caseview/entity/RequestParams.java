package com.uc.caseview.entity;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.uc.caseview.view.Action;

import java.util.ArrayList;
import java.util.List;

public class RequestParams implements Parcelable {
    public static final String KEY_REQUEST = "REQUEST";

    private int action;
    private int position;
    private CaseItem caseItem;
    private String message;
    private Integer requestCount;
    private List<ImageItem> imageItems;
    public RequestParams(Action action){
        this(action, -1, null);
    }

    public RequestParams(Action action, int position, CaseItem caseItem){
        this(action, position, caseItem, null);
    }
    public RequestParams(Action action, int position, CaseItem caseItem, String message){
        this(action, position, caseItem, message,  null);
    }
    public RequestParams(Action action, int position, CaseItem caseItem, String message,
                         Integer requestCount){
        this(action, position, caseItem, message, requestCount, new ArrayList<ImageItem>());

    }
    public RequestParams(Action action, int position, CaseItem caseItem, String message,
                         Integer requestCount,
                         @NonNull List<ImageItem> imageItems) {
        this.action = action.getCode();
        this.position = position;
        this.caseItem = caseItem;
        this.message = message;
        this.requestCount = requestCount;
        this.imageItems=imageItems;
    }

    @Override
    public String toString() {
        StringBuilder builder=new StringBuilder();
        builder.append("-------").append(getClass().getSimpleName()).append("--------\n")
                .append("action=[").append(action).append("][").append(getAction()).append(']').append('\n')
                .append("categoryId=[").append(caseItem).append(']').append('\n')
                .append("position=[").append(position).append(']').append('\n')
                .append("request count=[").append(requestCount).append(']').append('\n')
                .append("message=[").append(message).append(']').append('\n')
                .append("image items:\n");
        if(imageItems.size()>0) {
            for (ImageItem item : imageItems) {
                builder.append('\t').append(item).append('\n');
            }
        } else {
            builder.append("\timage items are empty.");
        }
        builder.append("=====================");
        return builder.toString();
    }

    public Action getAction() {
        return Action.fromCode(action);
    }

    public void setAction(int action) {
        this.action = action;
    }
    public void setAction(Action action){
        this.action=action.getCode();
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public CaseItem getCaseItem() {
        return caseItem;
    }

    public void setCaseItem(CaseItem caseItem) {
        this.caseItem = caseItem;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getRequestCount() {
        return requestCount;
    }

    public void setRequestCount(Integer requestCount) {
        this.requestCount = requestCount;
    }

    public List<ImageItem> getImageItems() {
        return imageItems;
    }

    public void setImageItems(List<ImageItem> imageItems) {
        this.imageItems = imageItems;
    }

    public  List<ImageItem> getSelectedItems(){
        List<ImageItem> selectedItems=new ArrayList<>();
        for(ImageItem item : imageItems){
            if(item.isSelected()) selectedItems.add(item);
        }
        return selectedItems;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.action);
        dest.writeInt(this.position);
        dest.writeParcelable(this.caseItem, flags);
        dest.writeString(this.message);
        dest.writeValue(this.requestCount);
        dest.writeTypedList(this.imageItems);
    }

    protected RequestParams(Parcel in) {
        this.action = in.readInt();
        this.position = in.readInt();
        this.caseItem = in.readParcelable(CaseItem.class.getClassLoader());
        this.message = in.readString();
        this.requestCount = (Integer) in.readValue(Integer.class.getClassLoader());
        this.imageItems = in.createTypedArrayList(ImageItem.CREATOR);
    }

    public static final Creator<RequestParams> CREATOR = new Creator<RequestParams>() {
        @Override
        public RequestParams createFromParcel(Parcel source) {
            return new RequestParams(source);
        }

        @Override
        public RequestParams[] newArray(int size) {
            return new RequestParams[size];
        }
    };
}
