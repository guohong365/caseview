package com.uc.caseview.view;

public enum Action {
    NONE(0),
    LIST_CASE(1),
    CREATE_NEW_CASE(2),
    OPERATE_ON_DETAIL(3),
    TAKE_PHOTO(4),
    CHOOSE_PHOTOS(5),
    COMPARE_SPLIT(6),
    COMPARE_STACK_UP(7),
    DELETE_PHOTOS(8),
    EXPORT_PHOTOS(9),
    MODIFY_CASE(10),
    TAKE_THUMBNAIL(11),
    LAST(11),
    ACTION_BAD(-1);
    private int code;
    Action(int code){
        this.code=code;
    }
    public int getCode(){
        return code;
    }
    static Action[] actions={
            NONE,
            LIST_CASE,
            CREATE_NEW_CASE,
            OPERATE_ON_DETAIL,
            TAKE_PHOTO,
            CHOOSE_PHOTOS,
            COMPARE_SPLIT,
            COMPARE_STACK_UP,
            DELETE_PHOTOS,
            EXPORT_PHOTOS,
            MODIFY_CASE,
            TAKE_THUMBNAIL
    };
    public static Action fromCode(int code){
        if(code >=0 && code <= LAST.code) return actions[code];
        return ACTION_BAD;
    }
}
