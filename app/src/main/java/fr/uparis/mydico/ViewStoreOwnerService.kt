package fr.uparis.mydico

import androidx.lifecycle.*


open class ViewStoreOwnerService : LifecycleService(), ViewModelStoreOwner {

    private val mViewModelStore = ViewModelStore()
    //var mFactory: Factory? = null

    override fun getViewModelStore(): ViewModelStore {
        return mViewModelStore
    }

    override fun onCreate() {
        super.onCreate()
        lifecycle.addObserver(object : LifecycleEventObserver {
            override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
                if (source.lifecycle.currentState == Lifecycle.State.DESTROYED) {
                    mViewModelStore.clear()
                    source.lifecycle.removeObserver(this)
                }
            }
        })
    }
}