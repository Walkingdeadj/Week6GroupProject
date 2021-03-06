package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Buyer;
import model.CarList;

/**
 * Servlet implementation class NavigationServlet
 */
@WebServlet("/navigationServlet")
public class NavigationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NavigationServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = "/viewAllCarsServlet";
		CarListHelper dao = new CarListHelper();
		BuyerHelper buyerDao = new BuyerHelper();
		CarSaleHelper saleDao = new CarSaleHelper();
		String act = request.getParameter("doThisToCar");

		if (act.equals("delete")) {
			try {
				Integer tempId = Integer.parseInt(request.getParameter("id"));
				CarList carToDelete = dao.searchForCarById(tempId);
				dao.deleteCar(carToDelete);

			} catch (NumberFormatException e) {
				System.out.println("Forgot to select a car");
			}

		} else if (act.equals("edit")) {
			try {
				Integer tempId = Integer.parseInt(request.getParameter("id"));
				CarList carToEdit = dao.searchForCarById(tempId);
				request.setAttribute("carToEdit", carToEdit);
				path = "/edit-car.jsp";
			} catch (NumberFormatException e) {
				System.out.println("Forgot to select a car");
			}

		} else if (act.equals("add")) {
			path = "/index.html";

		}
		else if (act.equals("deleteBuyer")) {
			try {
				Integer tempId = Integer.parseInt(request.getParameter("id"));
				Buyer buyerToDelete = buyerDao.searchForBuyerByID(tempId);
				buyerDao.deleteBuyer(buyerToDelete);
				 path = "/viewAllBuyersServlet";
				 
				 
			} catch (NumberFormatException e) {
				System.out.println("Forgot to select a buyer");
			}

		} else if (act.equals("editBuyer")) {
			try {
				Integer tempId = Integer.parseInt(request.getParameter("id"));
				Buyer buyerToEdit = buyerDao.searchForBuyerByID(tempId);
				request.setAttribute("buyerToEit", buyerToEdit);
				path = "/edit-buyer.jsp";
			} catch (NumberFormatException e) {
				System.out.println("Forgot to select a buyer");
			}

		} else if (act.equals("addBuyer")) {
			path = "/add-buyer.jsp";

		}
		
		else if (act.equals("addSale")) {			
			List<CarList> allCars = dao.showAllCars();//get all of the cars
			request.setAttribute("allCars", allCars);
			List<Buyer> allBuyers = buyerDao.showAllBuyers();//get all of the buyers
			request.setAttribute("allBuyers", allBuyers);			
			path = "/add-sale.jsp";//go to the add sale page
		}
		else if (act.equals("deleteSale")) {
			try {
				Integer tempId = Integer.parseInt(request.getParameter("id"));
				saleDao.deleteByID(tempId);
				 path = "/viewAllSalesServlet";				 				
			} catch (NumberFormatException e) {
				System.out.println("Forgot to select a sale");
			}

		}
		getServletContext().getRequestDispatcher(path).forward(request,response);
	}

}
