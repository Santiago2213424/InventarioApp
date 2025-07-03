package com.market.inventarioapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.market.inventarioapp.databinding.ActivityMapsBinding

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Activar solo los controles de zoom
        mMap.uiSettings.isZoomGesturesEnabled = true
        mMap.uiSettings.isZoomControlsEnabled = true
        mMap.uiSettings.isCompassEnabled = false
        mMap.uiSettings.isMyLocationButtonEnabled = false

        // Agregar 3 tiendas en Trujillo
        val tienda1 = LatLng(-8.111, -79.028) // Centro Histórico
        val tienda2 = LatLng(-8.104, -79.027) // Av. Larco
        val tienda3 = LatLng(-8.106, -79.035) // Cerca a UPAO

        mMap.addMarker(MarkerOptions().position(tienda1).title("Tienda Centro Histórico"))
        mMap.addMarker(MarkerOptions().position(tienda2).title("Tienda Av. Larco"))
        mMap.addMarker(MarkerOptions().position(tienda3).title("Tienda UPAO"))

        // Centrar y acercar el mapa en Trujillo
        val trujilloCentro = LatLng(-8.108, -79.030)
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(trujilloCentro, 15.5f))
    }
}
