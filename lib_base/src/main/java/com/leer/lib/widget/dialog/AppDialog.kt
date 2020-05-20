package com.leer.lib.widget.dialog

import android.app.Dialog
import android.content.Context
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.TextView
import com.leer.lib.R
import com.leer.lib.utils.KeyboardUtils
import com.leer.lib.utils.ScreenUtils
import kotlinx.android.synthetic.main.dialog_app.view.*

/**
 * Describe：自定义对话框
 * Created by Leer on 2018年9月27日18:13:09
 */
class AppDialog @JvmOverloads constructor(
    private val mContext: Context,
    @field:DialogType.Type @param:DialogType.Type private val type: Int = DialogType.DEFAULT
) : View.OnClickListener {

    private var dialogView: View = View.inflate(mContext, R.layout.dialog_app, null)

    private var maxCount = 0
    private var minCount = 0
    private val dialog: Dialog?
    private var leftListener: OnButtonClickListener? = null
    private var rightListener: OnButtonClickListener? = null
    private var itemClickListener: OnItemClickListener? = null
    private var title //标题
            : String? = null
    var isShowing = false
        private set


    init {
        var themeResId = R.style.DialogStyle
        if (type == DialogType.BOTTOM_IN) {
            themeResId = R.style.ActionSheetDialogStyle
            dialogView.minimumWidth = ScreenUtils.getScreenWidth()
        }
        dialog = Dialog(mContext, themeResId)
        dialog.setContentView(dialogView)
        val window = dialog.window
        if (window != null) {
            if (type == DialogType.BOTTOM_IN) {
                window.attributes.gravity = Gravity.BOTTOM
                val lp = window.attributes
                lp.x = 0
                lp.y = 0
                window.attributes = lp
            } else {
                window.attributes.gravity = Gravity.CENTER
            }
        }
        dialog.setCanceledOnTouchOutside(false)

        dialogView.btn_left.setOnClickListener(this)
        dialogView.btn_right.setOnClickListener(this)
        dialogView.minus.setOnClickListener(this)
        dialogView.plus.setOnClickListener(this)
        dialogView.tv_cancel.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_left -> {
                onButtonClick(leftListener)
            }
            R.id.btn_right -> {
                onButtonClick(rightListener)
            }
            R.id.minus -> {
                //减少
                var count = countEdtValue
                if (countEdtValue > minCount) {
                    dialogView.edt_count.setText((--count).toString())
                }
                judgeTheViews(count)
            }
            R.id.plus -> {
                //增加
                var count = countEdtValue
                if (count < maxCount) {
                    dialogView.edt_count.setText((++count).toString())
                }
                judgeTheViews(count)
            }
            R.id.tv_cancel -> {
                dismiss()
            }
        }
    }

    private fun initView() {
        when (type) {
            DialogType.DEFAULT -> setTitleText()
            DialogType.INPUT -> {
                setTitleText()
                dialogView.edt_input.visibility = View.VISIBLE
                dialogView.tv_content.visibility = View.GONE
                dialogView.ll_count_view.visibility = View.GONE
                dialog!!.window
                    ?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)
            }
            DialogType.COUNT -> {
                setTitleText()
                dialogView.edt_input.visibility = View.GONE
                dialogView.tv_content.visibility = View.GONE
                dialogView.ll_count_view.visibility = View.VISIBLE
                dialog!!.window
                    ?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)
            }
            DialogType.NO_TITLE -> dialogView.tv_title.visibility = View.GONE
            DialogType.BOTTOM_IN -> {
                //底部弹出对话框
                dialogView.tv_bottom_title.text = title
                dialogView.ll_center.visibility = View.GONE
                dialogView.ll_bottom!!.visibility = View.VISIBLE
            }
            else -> {
            }
        }
    }

    private fun setTitleText() {
        if (!TextUtils.isEmpty(title)) {
            dialogView.tv_title.text = title
        }
    }

    /**
     * 设置Title
     */
    fun setTitle(title: String?): AppDialog {
        this.title = title
        return this
    }

    /**
     * 设置Title
     */
    fun setEditText(text: String?): AppDialog {
        dialogView.edt_input.setText(text)
        return this
    }

    /**
     * 设置显示内容
     */
    fun setContent(content: String?): AppDialog {
        dialogView.tv_content.text = content
        return this
    }

    /**
     * 加减输入模式下 设置的数字
     *
     * @param minCount minCount
     * @param maxCount maxCount
     * @param current  current
     */
    fun setNumber(minCount: Int, maxCount: Int, current: Int): AppDialog {
        this.minCount = minCount
        this.maxCount = maxCount
        dialogView.edt_count.setText(current.toString())
        dialogView.edt_count.setSelection(dialogView.edt_count.text.length)
        dialogView.edt_count.addTextChangedListener(textWatcher)
        judgeTheViews(current)
        return this
    }

    /**
     * 显示对话框
     */
    fun show() {
        initView()
        dialog!!.show()
        isShowing = true
    }

    /**
     * 关闭对话框
     */
    fun dismiss() {
        if (dialog != null) {
            dialog.dismiss()
            isShowing = true
        }
    }

    /**
     * 给左边按钮设置文字 事件
     *
     * @param text     文字
     * @param listener 事件
     * @return AppDialog
     */
    fun setLeftButton(text: String?, listener: OnButtonClickListener?): AppDialog {
        leftListener = listener
        if (!TextUtils.isEmpty(text)) {
            dialogView.btn_left.text = text
        }
        return this
    }

    /**
     * 给左边按钮设置文字 事件
     *
     * @param listener 事件
     * @return AppDialog
     */
    fun setLeftButton(listener: OnButtonClickListener?): AppDialog {
        return setLeftButton(null, listener)
    }

    /**
     * 给左边按钮设置文字 事件
     *
     * @param text text
     * @return AppDialog
     */
    fun setLeftButton(text: String?): AppDialog {
        return setLeftButton(text, null)
    }

    /**
     * 给右边按钮设置文字 事件
     *
     * @param text     文字
     * @param listener 事件
     * @return AppDialoga
     */
    fun setRightButton(text: String?, listener: OnButtonClickListener?): AppDialog {
        rightListener = listener
        if (!TextUtils.isEmpty(text)) {
            dialogView.btn_right.text = text
        }
        return this
    }

    /**
     * 给右边按钮设置文字 事件
     *
     * @param listener 事件
     * @return AppDialog
     */
    fun setRightButton(listener: OnButtonClickListener?): AppDialog {
        return setRightButton(null, listener)
    }

    /**
     * 给右边按钮设置文字 事件
     *
     * @param text text
     * @return AppDialog
     */
    fun setRightButton(text: String?): AppDialog {
        return setRightButton(text, null)
    }

    /**
     * 左按钮文字颜色
     *
     * @param color color
     * @return AppDialog
     */
    fun setLeftButtonTextColor(color: Int): AppDialog {
        dialogView.btn_left.textSize = mContext.resources.getColor(color).toFloat()
        return this
    }

    /**
     * 右按钮文字大小
     *
     * @param color color
     * @return AppDialog
     */
    fun setRightButtonTextColor(color: Int): AppDialog {
        dialogView.btn_right.textSize = mContext.resources.getColor(color).toFloat()
        return this
    }

    /**
     * 单按钮文字大小
     *
     * @param color color
     * @return AppDialog
     */
    fun setSingleButtonTextColor(color: Int): AppDialog {
        return setRightButtonTextColor(color)
    }

    /**
     * 单按钮
     *
     * @param text     text
     * @param listener listener
     * @return AppDialog
     */
    fun setSingleButton(text: String?, listener: OnButtonClickListener?): AppDialog {
        rightListener = listener
        if (!TextUtils.isEmpty(text)) {
            dialogView.btn_right.text = text
        }
        dialogView.btn_left.visibility = View.GONE
        dialogView.btn_line.visibility = View.GONE
        dialogView.btn_right.setBackgroundResource(R.drawable.corners_white_gray_selecter)
        return this
    }

    /**
     * 单按钮
     *
     * @param listener listener
     * @return AppDialog
     */
    fun setSingleButton(listener: OnButtonClickListener?): AppDialog {
        return setSingleButton(null, listener)
    }

    /**
     * 单按钮
     *
     * @param text listener
     * @return AppDialog
     */
    fun setSingleButton(text: String?): AppDialog {
        return setSingleButton(text, null)
    }

    /**
     * 单按钮
     *
     * @return AppDialog
     */
    fun setSingleButton(): AppDialog {
        return setSingleButton(null, null)
    }

    /**
     * 设置底部弹出对话框的条目
     *
     * @param items    条目名称
     * @param listener listener
     * @return AppDialog
     */
    fun setBottomItems(
        items: List<String>?,
        listener: OnItemClickListener?
    ): AppDialog {
        itemClickListener = listener
        setItems(items)
        return this
    }

    /**
     * 设置底部弹出对话框的取消文字
     *
     * @param text text
     * @return AppDialog
     */
    fun setBottomCancelText(text: String?): AppDialog {
        if (!TextUtils.isEmpty(text)) {
            dialogView.tv_cancel.text = text
        }
        return this
    }

    /**
     * 设置底部弹出对话框的取消文字颜色
     *
     * @param color text
     * @return AppDialog
     */
    fun setBottomCancelTextColor(color: Int): AppDialog {
        dialogView.tv_cancel.setTextColor(mContext.resources.getColor(color))
        return this
    }

    /**
     * 给整个Dialog添加View
     *
     * @param view view
     * @return AppDialog
     */
    fun addDialogView(view: View?): AppDialog {
        dialogView.dialog_layout!!.removeAllViews()
        dialogView.dialog_layout!!.addView(view)
        return this
    }

    /**
     * 中间弹出对话框添加View
     *
     * @param view view
     * @return AppDialog
     */
    fun addContentView(view: View?): AppDialog {
        dialogView.ll_content_layout!!.removeAllViews()
        dialogView.ll_content_layout!!.addView(view)
        return this
    }

    /**
     * 底部弹出对话框添加View
     *
     * @param view     view
     * @param itemSize 条目数量
     * @return AppDialog
     */
    fun addItemView(view: View?, itemSize: Int): AppDialog {
        dialogView.ll_context.removeAllViews()
        dialogView.ll_context.addView(view)
        setItemScrollViewHeight(itemSize)
        return this
    }

    /**
     * 加减模式下点击结束结算值
     */
    private fun judgeTheViews(count: Int) {
        if (count == minCount) {
            dialogView.iv_minus.setImageResource(R.mipmap.input_minus_disabled)
        } else {
            dialogView.iv_minus.setImageResource(R.mipmap.input_minus_default)
        }
        if (count == maxCount) {
            dialogView.iv_plus.setImageResource(R.mipmap.input_add_disabled)
        } else {
            dialogView.iv_plus.setImageResource(R.mipmap.input_add_default)
        }
    }

    /**
     * 给点击事件设置数据
     *
     * @param listener listener
     */
    private fun onButtonClick(listener: OnButtonClickListener?) {
        if (type == DialogType.COUNT) {
            if (countEdtValue >= minCount) {
                listener?.onClick(countEdtValue.toString())
            }
            KeyboardUtils.hideSoftInput(dialogView.edt_count)
        } else if (type == DialogType.INPUT) {
            listener?.onClick(dialogView.edt_input.text.toString().trim { it <= ' ' })
            KeyboardUtils.hideSoftInput(dialogView.edt_input)
        } else {
            listener?.onClick(dialogView.tv_content.text.toString())
        }
        dismiss()
    }

    /**
     * 底部弹出对话框添加条目
     *
     * @param items items
     */
    private fun setItems(items: List<String>?) {
        if (items != null && items.size > 0) {
            val size = items.size
            setItemScrollViewHeight(size)
            // 循环添加条目
            for (i in 0..size - 1) {
                val v = View.inflate(
                    mContext,
                    R.layout.layout_item_of_dialog_bottom_in,
                    null
                )
                val item =
                    v.findViewById<TextView>(R.id.tv_text)
                item.text = items[i]
                item.tag = i
                item.setOnClickListener { v ->
                    if (itemClickListener != null) {
                        itemClickListener!!.onItemClick(v.tag as Int)
                    }
                    dismiss()
                }
                dialogView.ll_context.addView(v)
            }
        }
    }

    /**
     * 设置底部弹出带条目的ScrollView的高度
     *
     * @param size 条目数量
     */
    private fun setItemScrollViewHeight(size: Int) {
        // 添加条目过多的时候控制高度
        if (size >= 7) {
            val params =
                dialogView.sLayout_content.layoutParams as LinearLayout.LayoutParams
            params.height = ScreenUtils.getScreenHeight() / 2
            dialogView.sLayout_content.layoutParams = params
        }
    }

    /**
     * 得到 加减模式下 输入框的值
     *
     * @return int
     */
    private val countEdtValue: Int
        private get() {
            var count = 0
            val text = dialogView.edt_count.text.toString().trim { it <= ' ' }
            if (!TextUtils.isEmpty(text)) {
                count = text.toInt()
            }
            return count
        }

    /**
     * 加减模式下输入框的监听
     */
    private val textWatcher: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(
            s: CharSequence,
            start: Int,
            count: Int,
            after: Int
        ) {
        }

        override fun onTextChanged(
            s: CharSequence,
            start: Int,
            before: Int,
            count: Int
        ) {
        }

        override fun afterTextChanged(s: Editable) {
            if (!TextUtils.isEmpty(s.toString().trim { it <= ' ' })) {
                dialogView.edt_count.removeTextChangedListener(this)
                val text = s.toString().toInt()
                if (text < minCount) {
                    dialogView.edt_count.setText(minCount.toString())
                } else if (text > maxCount) {
                    dialogView.edt_count.setText(maxCount.toString())
                }
                dialogView.edt_count.setSelection(dialogView.edt_count.text.length)
                dialogView.edt_count.addTextChangedListener(this)
                judgeTheViews(dialogView.edt_count.text.toString().trim { it <= ' ' }.toInt())
            } else {
                //删光了 减 按钮不可点击
                dialogView.iv_minus.setImageResource(R.mipmap.input_minus_disabled)
            }
        }
    }

    /**
     * 按钮被点击事件回调
     */
    interface OnButtonClickListener {
        fun onClick(`val`: String?)
    }

    /**
     * 底部弹出对话框条目被点击
     */
    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }
}