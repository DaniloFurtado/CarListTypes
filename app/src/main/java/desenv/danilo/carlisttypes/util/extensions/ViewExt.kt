package desenv.danilo.carlisttypes.util.extensions

import android.view.View


fun View.show() : View {
    if (this.visibility != View.VISIBLE) {
        this.visibility = View.VISIBLE
    }
    return this
}

fun View.hide() : View {
    if (this.visibility != View.INVISIBLE) {
        this.visibility = View.INVISIBLE
    }
    return this
}

fun View.gone() : View {
    if (this.visibility != View.GONE) {
        this.visibility = View.GONE
    }
    return this
}