package by.huk.crypto_currencies.ui.utils

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.support.annotation.DrawableRes
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.doOnPreDraw
import androidx.databinding.BindingAdapter
import androidx.fragment.app.Fragment
import androidx.vectordrawable.graphics.drawable.Animatable2Compat
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat
import by.huk.crypto_currencies.R.drawable.shape_green
import by.huk.crypto_currencies.R.drawable.shape_white

@BindingAdapter("setVisibleGone")
fun ImageView.setVisibleGone(isVisible: Boolean) {
    if (isVisible) this.visibility = View.VISIBLE
    else this.visibility = View.GONE
}

@BindingAdapter("setBackground")
fun TextView.setBackground(itIsCurrentItem: Boolean) {
    if (itIsCurrentItem) {
        this.setTextColor(Color.WHITE)
    } else {
        this.setTextColor(Color.GRAY)
    }

}

fun ImageView.applyLoopingAnimatedVectorDrawable(@DrawableRes avdResId: Int) {
    val animated = AnimatedVectorDrawableCompat.create(context, avdResId)
    animated?.registerAnimationCallback(object : Animatable2Compat.AnimationCallback() {
        override fun onAnimationEnd(drawable: Drawable?) {
            this@applyLoopingAnimatedVectorDrawable.post { animated.start() }
        }
    })
    this.setImageDrawable(animated)
    animated?.start()
}
fun Fragment.waitForTransition(targetView: View) {
    postponeEnterTransition()
    targetView.doOnPreDraw { startPostponedEnterTransition() }
}