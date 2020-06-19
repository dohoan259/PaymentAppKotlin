package me.hp.paymentapp.ui.main.scan

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.gson.Gson
import com.google.zxing.BarcodeFormat
import com.google.zxing.ResultPoint
import com.google.zxing.client.android.BeepManager
import com.google.zxing.integration.android.IntentIntegrator
import com.journeyapps.barcodescanner.*
import me.hp.paymentapp.R
import me.hp.paymentapp.data.uimodel.OrderItemUI
import me.hp.paymentapp.ui.base.BaseFragment
import java.text.ParseException
import java.util.*
import javax.inject.Inject
import android.Manifest.permission
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.core.content.ContextCompat
import com.google.gson.JsonSyntaxException


class ScanFragment : BaseFragment() {

    companion object {

        const val RC_HANDLE_CAMERA_PERM = 10002
        val permissions = arrayOf<String>(permission.CAMERA)
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: ScanViewModel



    private lateinit var barcodeScannerView: DecoratedBarcodeView
    private lateinit var beepManager: BeepManager
    private var lastText: String? = null
    private lateinit var integrator: IntentIntegrator

    private val callback = object : BarcodeCallback {
        override fun barcodeResult(result: BarcodeResult) {
            lastText = result.text
            beepManager.playBeepSoundAndVibrate()

            try {
                val orderItem = Gson().fromJson<OrderItemUI>(lastText, OrderItemUI::class.java)
                val action = ScanFragmentDirections.actionNavigationScanToPayActivity(orderItem)
                findNavController().navigate(action)
            } catch (ex: ParseException) {
                Toast.makeText(requireContext(), "QR code format is wrong. Please try again", Toast.LENGTH_SHORT).show()
            } catch (ex: JsonSyntaxException) {
                Toast.makeText(requireContext(), "QR code format is wrong. Please try again", Toast.LENGTH_SHORT).show()
            }
        }

        override fun possibleResultPoints(resultPoints: List<ResultPoint>) {}
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        beepManager = BeepManager(requireActivity())
        integrator = IntentIntegrator(requireActivity())
        integrator.setOrientationLocked(true)
        integrator.captureActivity = requireActivity().javaClass
        integrator.initiateScan()

        viewModel = ViewModelProvider(this, viewModelFactory).get(ScanViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_scan, container, false)
        barcodeScannerView = root.findViewById(R.id.barcode_scanner_view)
        integrator.initiateScan()
        val formats = Arrays.asList(BarcodeFormat.QR_CODE, BarcodeFormat.CODE_39)
        barcodeScannerView.statusView.visibility = View.GONE
        barcodeScannerView.barcodeView.decoderFactory = DefaultDecoderFactory(formats)
        barcodeScannerView.decodeSingle(callback)
        barcodeScannerView.initializeFromIntent(requireActivity().intent)

        return root
    }

    override fun onStart() {
        super.onStart()
        barcodeScannerView.decodeSingle(callback)
        barcodeScannerView.initializeFromIntent(requireActivity().intent)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(requireActivity().applicationContext, permission.CAMERA)
                == PackageManager.PERMISSION_DENIED) {
                ActivityCompat.requestPermissions(requireActivity(),
                    permissions,
                    RC_HANDLE_CAMERA_PERM
                )
            }
        }
    }

    override fun onResume() {
        super.onResume()
        barcodeScannerView.resume()
    }

    override fun onPause() {
        super.onPause()
        barcodeScannerView.pause()
    }
}