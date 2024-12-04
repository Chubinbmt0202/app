package HOME;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.apprestaurant.R;

import java.util.ArrayList;

public class Searach_Fragment extends Fragment {
    // khai báo view
    private View view;
    private String searchHistory = null;
    private LinearLayout lineSearchHistory, lineSeparator, lineSearchResult;
    private EditText edtSearchHistory;
    private ListView lvHistory;
    private Apdatee_SearchHistory adapter;
    private ImageView imgBackSearch;
    private ArrayList<String> list;

    public Searach_Fragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_searach_, container, false);
        lvHistory = view.findViewById(R.id.lvhistory);
        edtSearchHistory = view.findViewById(R.id.edtsearch1);
        lineSearchHistory = view.findViewById(R.id.linehistory);
        lineSeparator = view.findViewById(R.id.lineseparate);
        lineSearchResult = view.findViewById(R.id.lineketquaseach);

        lineSearchHistory.setVisibility(View.GONE);
        lineSearchResult.setVisibility(View.GONE);
        lineSeparator.setVisibility(View.GONE);

        // Đọc dữ liệu từ SharedPreferences
        SharedPreferences sp = requireContext().getSharedPreferences("Mysearchhistoryy", Context.MODE_PRIVATE);
        searchHistory = sp.getString("timkiem", "");
        list = new ArrayList<>();
        if (searchHistory != null && !searchHistory.isEmpty()) {
            String[] parts = searchHistory.split("/");
            for (String part : parts) {
                if (!part.isEmpty()) list.add(part);
            }
            adapter = new Apdatee_SearchHistory(requireContext(), R.layout.layout_historysearch, list);
            lvHistory.setAdapter(adapter);
            lineSearchHistory.setVisibility(View.VISIBLE);
        }

        edtSearchHistory.setOnEditorActionListener((textView, actionId, keyEvent) -> {
            String input = edtSearchHistory.getText().toString().trim();
            if (!input.isEmpty() && !list.contains(input)) {
                searchHistory += input + "/";
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("timkiem", searchHistory);
                editor.apply();

                list.add(input);
                adapter.notifyDataSetChanged();
                lineSearchHistory.setVisibility(View.VISIBLE);
            }
            return false;
        });

        setupBackButton();
        return view;
    }

    private void setupBackButton() {
        imgBackSearch = view.findViewById(R.id.imgbacksearch);
        imgBackSearch.setOnClickListener(view -> {
            if (requireActivity().getSupportFragmentManager().getBackStackEntryCount() > 0) {
                requireActivity().getSupportFragmentManager().popBackStack();
            }
        });
    }
}
