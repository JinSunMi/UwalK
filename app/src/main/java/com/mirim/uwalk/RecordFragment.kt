package com.mirim.uwalk

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.PermissionChecker.checkSelfPermission
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mirim.uwalk.databinding.FragmentRecordBinding
import com.mirim.uwalk.model.Park


class RecordFragment: Fragment() {
    lateinit var binding: FragmentRecordBinding

    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<CustomAdapter.ViewHolder>? = null

    private var lat: Double = 0.0
    private var lon: Double = 0.0

    private lateinit var locationManager: LocationManager
    private lateinit var locationListener: LocationListener

    private val PERMISSION_REQUEST_CODE: Int = 1


    var list = mutableListOf<Park>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRecordBinding.inflate(inflater, container, false)
        val view = binding.root

        locationManager = requireContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager

        list.add(Park("Potters Fields Park", "189 Tooley St, London SE1 2UD", 51.502957, -0.0805889))
        list.add(Park("Gasholder Park", "London N1C 4AB", 51.5370186, -0.1303262))
        list.add(Park("Markfield Park", "43 Crowland Rd, London N15 6UL", 51.5811524, -0.0654504))
        list.add(Park("Jubilee Park", "Belvedere Rd, London SE1 7PG", 51.504513, -0.1217479))
        list.add(Park("Backton Park", "E6 5QJ London", 51.5162329, 0.0497618))
        list.add(Park("Jubilee Country Park", "Thornet Wood Rd, London BR5 1DA", 51.3937354, 0.0542626))
        list.add(Park("Woodfield Park", "Cool Oak Ln, London NW9 7BH", 53.4953176, -1.1496276))
        list.add(Park("Whitehall Gardens", "Victoria Embankment, London SW1A 2HE", 53.317686, -0.9464235))
        list.add(Park("Trinity Square Gardens", "Tower Hill Station, 38 Trinity Square, London EC3N 4DJ", 51.5098352, -0.079841))
        list.add(Park("London Fields", "London Fields West Side, London E8 3EU", 51.5408682, -0.0621984))

        locationListener = object : LocationListener {
            override fun onLocationChanged(p0: Location) {
                lat = p0.latitude
                lon = p0.longitude

                locationManager.removeUpdates(this)
                layoutManager = LinearLayoutManager(context)

                binding.recyclerView.layoutManager = layoutManager
                adapter= CustomAdapter(requireContext(), list, lat, lon)

                binding.recyclerView.adapter = adapter
            }
        }
        if(ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) === PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) === PackageManager.PERMISSION_GRANTED) {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0f, locationListener)
        }
        else {
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(android.Manifest.permission.ACCESS_COARSE_LOCATION, android.Manifest.permission.ACCESS_FINE_LOCATION), PERMISSION_REQUEST_CODE)
        }

        binding.toolbar.imageToolbarProfile.setOnClickListener {
            startActivity(Intent(context, MypageActivity::class.java))
        }

        return view
    }
    @SuppressLint("MissingPermission")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when(requestCode) {
            PERMISSION_REQUEST_CODE -> {
                var allPermissionGranted = true
                for(result in grantResults) {
                    allPermissionGranted = (result == PackageManager.PERMISSION_GRANTED)
                    if(!allPermissionGranted) break
                }
                if(allPermissionGranted) {
                    locationManager?.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0f, locationListener)
                }
                else {
                    Toast.makeText(requireContext(), "위치 정보 제공 동의가 필요합니다.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

}