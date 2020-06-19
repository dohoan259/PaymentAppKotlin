package me.hp.paymentapp.ui.payment

import android.os.Bundle
import androidx.navigation.findNavController
import me.hp.paymentapp.R
import me.hp.paymentapp.ui.base.BaseActivity

class PayActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pay)

        intent.extras?.let {
            val navController = findNavController(R.id.nav_host_fragment)
            val orderItem = PayActivityArgs.fromBundle(it).orderItem
            val args = Bundle()
            args.putSerializable("orderItem", orderItem)
            navController.setGraph(navController.graph, args)
        }
    }
}
