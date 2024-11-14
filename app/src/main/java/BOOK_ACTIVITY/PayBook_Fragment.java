package BOOK_ACTIVITY;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.apprestaurant.R;

import fragment_ngv.BookFragment;
import fragment_ngv.OrderFragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PayBook_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PayBook_Fragment extends Fragment {

    private View view;
    private LinearLayout linepay;
    private Button btnpay;

    // Ngân hàng đã liên kết
    private CheckBox cbpay;

    // Khai bao cardcview ngan hang
    private CardView cvvt , cvmb , cvvtb , cvvcb , cvarb, cvtcb;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PayBook_Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PayBook_Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PayBook_Fragment newInstance(String param1, String param2) {
        PayBook_Fragment fragment = new PayBook_Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_pay_book_, container, false);
        PayThanhCong();
        Viettin();
        QuayLai();
        return view;
    }

    private void PayThanhCong()
    {
        btnpay = view.findViewById(R.id.btn_pay);
        cbpay = view.findViewById(R.id.cbpay);
            btnpay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (cbpay.isChecked() == false) {
                        AlertDialog.Builder ab = new AlertDialog.Builder(getContext());
                        ab.setMessage("Vui lòng thanh toán");
                        ab.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });
                        ab.show();
                    } else {
                        Fragment newFragment = new Booksuccessfully_Fragment(); // Replace with your XuFragment class
                        FragmentManager fragmentManager = getParentFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.ngv_viewPager, newFragment);
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();
                    }
                }
            });
    }

    private void QuayLai()
    {

        linepay = view.findViewById(R.id.linepay);
        linepay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment newFragment = new BookFragment(); // Replace with your XuFragment class
                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.ngv_viewPager, newFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
    }

    private void Viettin()
    {
        cvvt = view.findViewById(R.id.cv04);
        cvvt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                View dialogView = LayoutInflater.from(getActivity()).inflate(R.layout.layout_pay, null, false);


                AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
                alertDialog.setView(dialogView);
                alertDialog.show();
                final ImageView imgview = dialogView.findViewById(R.id.imageView19);
                imgview.setImageResource(R.drawable.logo_vtb);

                final EditText edtstk = dialogView.findViewById(R.id.edtstk);
                final EditText edttentk = dialogView.findViewById(R.id.edttentk);
                final EditText edtngaycap = dialogView.findViewById(R.id.edtngaycap);
                final Button btnpay = dialogView.findViewById(R.id.btnpayy);

                btnpay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (edtstk.getText().toString().isEmpty() || edttentk.getText().toString().isEmpty() ||
                                edtngaycap.getText().toString().isEmpty()
                        ) {
                            Toast.makeText(getContext(), "Liên kết không thành công!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getContext(), "Liên kết thành công!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });
    }
 }
